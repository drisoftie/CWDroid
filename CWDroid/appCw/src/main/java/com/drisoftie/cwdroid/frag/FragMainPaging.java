/*
 * Copyright [2015] [Alexander Dridiger - drisoftie@gmail.com]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package com.drisoftie.cwdroid.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drisoftie.action.async.ActionBuilder;
import com.drisoftie.action.async.ActionMethod;
import com.drisoftie.action.async.BaseAsyncAction;
import com.drisoftie.action.async.IGenericAction;
import com.drisoftie.action.async.RegActionMethod;
import com.drisoftie.action.async.android.AndroidAction;
import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.api.AuthenticatedUser;
import com.drisoftie.cwdroid.api.CwApi;
import com.drisoftie.cwdroid.db.DomainHelper;
import com.drisoftie.cwdroid.domain.CwNavItem;
import com.drisoftie.cwdroid.domain.CwUser;
import com.drisoftie.cwdroid.domain.NavItemType;
import com.drisoftie.cwdroid.util.CredentialUtils;
import com.drisoftie.cwdroid.view.AdaptNavMenu;
import com.drisoftie.frags.INotificationForwarder;
import com.drisoftie.frags.comp.BaseAdaptFragPager;
import com.drisoftie.frags.comp.FragManagedPaging;
import com.drisoftie.frags.handler.IResultHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * @author Alexander Dridiger
 */
public class FragMainPaging extends FragManagedPaging {

    /*-###########
     * UI elements
     * ###########*/

    private AdaptNewsPager        adaptMainPager;
    private View                  drawerContent;
    private DrawerLayout          drawer;
    private ActionBarDrawerToggle toggle;
    private ListView              listDrawer;
    private AdaptNavMenu          listAdaptDrawer;
    private RecyclerView          recycDrawer;
    private TextView              txtUserName;
    private ImageView             imgUserIcon;
    private ImageView             imgLogout;

    /*-#######
     * Actions
     * #######*/

    private BaseAsyncAction<View, Void, Void, Void, Void>                actionUserLogin;
    private BaseAsyncAction<View, AuthenticatedUser, CwUser, Void, Void> actionCheckLogin;
    private BaseAsyncAction<View, Void, Void, Void, Void>                actionLogout;
    private BaseAsyncAction<View, Void, Void, Void, Void>                actionNavigationClick;


    @Override
    public void onCreating(View layout, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        ((ActionBarActivity) getActivity()).setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(getActivity(), (DrawerLayout) layout.findViewById(R.id.draw_main), toolbar,
                                           R.string.title_nav_menu_open, R.string.title_nav_menu_closed) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                syncState();
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                syncState();
                getActivity().supportInvalidateOptionsMenu();
            }
        };

        drawerContent = layout.findViewById(R.id.ll_nav_draw);

        drawer = (DrawerLayout) layout.findViewById(R.id.draw_main);
        drawer.setDrawerListener(toggle);

        listDrawer = (ListView) layout.findViewById(R.id.list_nav_draw_main);

        listAdaptDrawer = new AdaptNavMenu(getActivity(), R.id.txt_litem_nav_draw_item_name, new ArrayList<CwNavItem>());
        listDrawer.setAdapter(listAdaptDrawer);
        listDrawer.setItemChecked(0, true);

        actionBar().setDisplayHomeAsUpEnabled(true);
        actionBar().setHomeButtonEnabled(true);
        toggle.syncState();

        txtUserName = (TextView) layout.findViewById(R.id.txt_nav_draw_user_name);
        imgUserIcon = (ImageView) layout.findViewById(R.id.img_nav_draw_login_user_profile);
        imgLogout = (ImageView) layout.findViewById(R.id.img_nav_draw_logout_user_profile);

//        recycDrawer = (RecyclerView) findViewById(R.id.recyc_nav_draw_main);
//        recycDrawer.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mCrimes = CrimeLab.get(getActivity()).getCrimes();
//        recycDrawer.setAdapter(new AdaptRecycNavMenu());
    }

    @Override
    public void onResuming() {
    }

    @Override
    public void registerComponents() {
        drawer.openDrawer(drawerContent);
        initActions();
        checkUser();
    }

    /**
     * Most actions are created here.
     */
    @SuppressWarnings("unchecked")
    private void initActions() {
        List<Fragment> frags = getChildFragmentManager().getFragments();

        if (actionUserLogin == null) {
            actionUserLogin = new ActionBuilder().with(txtUserName, imgUserIcon).reg(View.OnClickListener.class,
                                                                                     RegActionMethod.SET_ONCLICK).reg(IGenericAction.class,
                                                                                                                      RegActionMethod.NONE)
                                                 .reg(IResultHandler.class, RegActionMethod.NONE).pack(
                            new AndroidAction<View, AuthenticatedUser, Void, Void, Void>(null, null) {

                                private DiagLogin diag;

                                @Override
                                public Object onActionPrepare(String methodName, Object[] methodArgs, Void tag1, Void tag2,
                                                              Object[] additionalTags) {
                                    if (ActionMethod.INVOKE_ACTION.matches(methodName)) {
                                        Object[] args = stripMethodArgs(methodArgs);
                                        if (ArrayUtils.isEmpty(args)) {
                                            if (diag == null) {
                                                diag = new DiagLogin();
                                                Bundle bundle = new Bundle();
                                                bundle.putInt(getString(R.string.bundl_diag_layout), R.layout.diag_login);
                                                bundle.putInt(getString(R.string.bundl_diag_title), R.string.diag_travel_bags_add);
                                                if (CwApp.domain().hasUser()) {
                                                    CwUser user = CwApp.domain().getLoggedInUser();
                                                    bundle.putString(getString(R.string.bundl_diag_input_name), user.getName());
                                                    bundle.putString(getString(R.string.bundl_diag_input_password), user.getPwHash());
                                                }
                                                bundle.putInt(getString(R.string.bundl_diag_btn_positive), R.string.save);
                                                bundle.putInt(getString(R.string.bundl_diag_btn_negative), R.string.cancel);
                                                bundle.putBoolean(diag.getClass().getName(), true);
                                                diag.setArguments(bundle);
                                                diag.setResultHandler(actionUserLogin.getHandlerImpl(IResultHandler.class));
                                                diag.show(getChildFragmentManager(), diag.getClass().getName());
                                            }
                                        } else {
                                            for (Object arg : args) {
                                                if (arg instanceof DiagLogin) {
                                                    diag = (DiagLogin) arg;
                                                }
                                            }
                                        }
                                        skipWorkThreadOnce();
                                    } else if (ActionMethod.ON_CLICK.matches(methodName)) {
                                        if (diag == null) {
                                            diag = new DiagLogin();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt(getString(R.string.bundl_diag_layout), R.layout.diag_login);
                                            bundle.putInt(getString(R.string.bundl_diag_title), R.string.diag_login);
                                            if (CwApp.domain().hasUser()) {
                                                CwUser user = CwApp.domain().getLoggedInUser();
                                                bundle.putString(getString(R.string.bundl_diag_input_name), user.getName());
                                                bundle.putString(getString(R.string.bundl_diag_input_password), user.getPwHash());
                                            }
                                            bundle.putInt(getString(R.string.bundl_diag_btn_positive), R.string.login);
                                            bundle.putInt(getString(R.string.bundl_diag_btn_negative), R.string.cancel);
                                            bundle.putBoolean(diag.getClass().getName(), true);
                                            diag.setArguments(bundle);
                                            diag.setResultHandler(actionUserLogin.getHandlerImpl(IResultHandler.class));
                                            diag.show(getChildFragmentManager(), diag.getClass().getName());
                                        }
                                        skipWorkThreadOnce();
                                    }
                                    return null;
                                }

                                @Override
                                public AuthenticatedUser onActionDoWork(String methodName, Object[] methodArgs, Void tag1, Void tag2,
                                                                        Object[] additionalTags) {
                                    if (ActionMethod.ON_RESULT_READY.matches(methodName)) {
                                        if (!ArrayUtils.isEmpty(methodArgs) && methodArgs[0] != null) {
                                            String[] inputs = (String[]) methodArgs[0];
                                            AuthenticatedUser result = CwApi.authenticate(inputs[0], inputs[1], true);
                                            if (result != null) {
                                                switch (result.getSuccess()) {
                                                    case YES:
                                                        byte[] key = CredentialUtils.generateKeyBytes();
                                                        String obfPw = CredentialUtils.obfuscateToBase64(key, inputs[1]);
                                                        String obfHash = CredentialUtils.obfuscateToBase64(key, result.getPasswordHash());

                                                        CwUser user;
                                                        if (CwApp.domain().hasUser()) {
                                                            user = CwApp.domain().getLoggedInUser();
                                                        } else {
                                                            user = new CwUser();
                                                        }
                                                        user.setName(inputs[0]);
                                                        user.setPassword(obfPw);
                                                        user.setPwHash(obfHash);
                                                        user.setKeyData(key);
                                                        user.setDate(GregorianCalendar.getInstance().getTime());
                                                        DomainHelper.createOrUpdate(CwUser.class, user);
                                                        if (!CwApp.domain().hasUser()) {
                                                            CwApp.domain().setLoggedInUser(user);
                                                        }
                                                        CwApp.domain().setLoggedIn(true);
                                                        break;
                                                }
                                            }
                                            return result;
                                        }
                                    }
                                    return null;
                                }

                                @Override
                                public void onActionAfterWork(String methodName, Object[] methodArgs, AuthenticatedUser result, Void tag1,
                                                              Void tag2, Object[] additionalTags) {
                                    if (ActionMethod.ON_RESULT_READY.matches(methodName)) {
                                        if (result != null) {
                                            switch (result.getSuccess()) {
                                                case YES:
                                                    uiLoggedIn(result.getUsername(), result.getUid());
                                                    break;
                                                case NO:
                                                    Toast.makeText(getActivity(), getString(R.string.toast_login_failed),
                                                                   Toast.LENGTH_SHORT).show();
                                                    break;
                                                case BANNED:
                                                    Toast.makeText(getActivity(), getString(R.string.toast_login_banned),
                                                                   Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        } else if (!ArrayUtils.isEmpty(methodArgs) && methodArgs[0] != null) {
                                            Toast.makeText(getActivity(), getString(R.string.toast_login_failed), Toast.LENGTH_SHORT)
                                                 .show();
                                        }
                                    }
                                    diag = null;
                                }
                            });
        } else {
            actionUserLogin.replaceViewTargets(txtUserName, imgUserIcon);
            actionUserLogin.registerAction();
        }

        actionCheckLogin = new ActionBuilder().with().reg(IGenericAction.class, RegActionMethod.NONE).pack(
                new AndroidAction<View, AuthenticatedUser, CwUser, Void, Void>(null, null) {
                    @Override
                    public Object onActionPrepare(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                        return null;
                    }

                    @Override
                    public AuthenticatedUser onActionDoWork(String methodName, Object[] methodArgs, Void tag1, Void tag2,
                                                            Object[] additionalTags) {
                        AuthenticatedUser loginResult = null;
                        CwUser            user;
                        if (CwApp.domain().hasUser()) {
                            user = CwApp.domain().getLoggedInUser();
                        } else {
                            user = DomainHelper.getFirst(CwUser.class);
                            CwApp.domain().setLoggedInUser(user);
                        }
                        if (user != null) {
                            String pw = CredentialUtils.deobfuscateFromBase64(user.getKeyData(), user.getPassword());
                            if (StringUtils.isNotEmpty(pw)) {
                                loginResult = CwApi.authenticate(user.getName(), pw, true);
                                if (loginResult != null) {
                                    switch (loginResult.getSuccess()) {
                                        case YES:
                                            CwApp.domain().setLoggedIn(true);
                                            postActionProgress(user, methodName, methodArgs);
                                            break;
                                        case NO:
                                        case BANNED:
                                            CwApp.domain().setLoggedIn(false);
                                            break;
                                    }
                                }
                            }
                        }
                        return loginResult;
                    }

                    @Override
                    public void onActionProgress(String methodName, CwUser progress, Object[] methodArgs, Void tag1, Void tag2,
                                                 Object[] additionalTags) {
                        uiLoggedIn(progress.getName(), progress.getId());
                    }

                    @Override
                    public void onActionAfterWork(String methodName, Object[] methodArgs, AuthenticatedUser result, Void tag1, Void tag2,
                                                  Object[] additionalTags) {
                        if (result != null) {
                            switch (result.getSuccess()) {
                                case YES:
                                    uiLoggedIn(result.getUsername(), result.getUid());
                                    break;
                                case NO:
                                    Toast.makeText(getActivity(), getString(R.string.toast_login_failed), Toast.LENGTH_SHORT).show();
                                    uiLoggedOut();
                                    break;
                                case BANNED:
                                    Toast.makeText(getActivity(), getString(R.string.toast_login_banned), Toast.LENGTH_SHORT).show();
                                    uiLoggedOut();
                                    break;
                            }
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.toast_login_failed), Toast.LENGTH_SHORT).show();
                            uiLoggedOut();
                        }
                    }
                });

        actionLogout = new ActionBuilder().with(imgLogout).reg(View.OnClickListener.class, RegActionMethod.SET_ONCLICK).reg(
                IGenericAction.class, RegActionMethod.NONE).pack(new AndroidAction<View, Void, Void, Void, Void>(null, null) {
            @Override
            public Object onActionPrepare(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                return null;
            }

            @Override
            public Void onActionDoWork(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                CwApp.domain().setLoggedIn(false);
                CwApp.domain().getLoggedInUser().resetCredentials();
                DomainHelper.createOrUpdate(CwUser.class, CwApp.domain().getLoggedInUser());
                return null;
            }

            @Override
            public void onActionAfterWork(String methodName, Object[] methodArgs, Void result, Void tag1, Void tag2,
                                          Object[] additionalTags) {
                uiLoggedOut();
                Toast.makeText(getActivity(), getString(R.string.toast_logout), Toast.LENGTH_SHORT).show();
            }
        });

        actionNavigationClick = new ActionBuilder().with(listDrawer).reg(AdapterView.OnItemClickListener.class,
                                                                         RegActionMethod.SET_ONITEMCLICKLISTENER).pack(
                new AndroidAction<View, Void, Void, Void, Void>(null, null) {

                    NavItemType currentType = NavItemType.NEWS;

                    @Override
                    public Object onActionPrepare(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                        int         pos  = (Integer) methodArgs[2];
                        NavItemType type = listAdaptDrawer.getItem(pos).getNavType();
                        listDrawer.setItemChecked(pos, true);
                        if (!type.equals(currentType)) {
                            switch (type) {
                                case NEWS:
                                    switchPagerAdapter(new AdaptNewsPager(getChildFragmentManager()));
                                    break;
                                case BLOGS:
                                    switchPagerAdapter(new AdaptBlogsPager(getChildFragmentManager()));
                                    break;
                            }
                            currentType = type;
                        }
                        drawer.closeDrawers();
                        skipWorkThreadOnce();
                        return null;
                    }

                    @Override
                    public Void onActionDoWork(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                        return null;
                    }

                    @Override
                    public void onActionAfterWork(String methodName, Object[] methodArgs, Void result, Void tag1, Void tag2,
                                                  Object[] additionalTags) {
                    }
                });

        if (frags != null) {
            for (Fragment fragment : frags) {
                if (fragment != null && fragment.getArguments() != null && fragment.getArguments().containsKey(DiagLogin.class.getName())) {
                    actionUserLogin.invokeSelf(fragment);
                    ((DiagLogin) fragment).setResultHandler(actionUserLogin.getHandlerImpl(IResultHandler.class));
                }
            }
        }
    }

    private void uiLoggedIn(String name, int id) {
        ImageLoader.getInstance().displayImage(getString(R.string.userpic_url, id, 100), imgUserIcon);
        txtUserName.setText(name);
        listAdaptDrawer.changeLoginMenu(true);
        imgLogout.setVisibility(View.VISIBLE);
    }

    private void uiLoggedOut() {
        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.ic_mock, imgUserIcon);
        txtUserName.setText("");
        listAdaptDrawer.changeLoginMenu(false);
        imgLogout.setVisibility(View.GONE);
    }

    private void checkUser() {
        if (actionCheckLogin != null && CwApp.domain().hasValidUser()) {
            actionCheckLogin.invokeSelf();
        }
    }

    @Override
    public void deregisterComponents() {
    }

    @Override
    public void onPausing() {
    }

    @Override
    public int getPagerLayoutId() {
        return R.layout.frag_pager;
    }

    @Override
    public int getViewPagerId() {
        return R.id.pager_main;
    }

    @Override
    public BaseAdaptFragPager getPagerAdapter(FragmentManager childFragmentManager) {
        adaptMainPager = new AdaptNewsPager(childFragmentManager);
        return adaptMainPager;
    }

    @Override
    public int getStartPage() {
        return 0;
    }

    @Override
    public INotificationForwarder getNotificationForwarder() {
        return null;
    }

    @Override
    public Integer getPageMargin() {
        return 4;
    }

    @Override
    public Integer getPageMarginDrawable() {
        return R.color.app_theme;
    }
}
