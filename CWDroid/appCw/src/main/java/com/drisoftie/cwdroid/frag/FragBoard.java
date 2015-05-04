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
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.drisoftie.action.async.ActionBuilder;
import com.drisoftie.action.async.BaseAsyncAction;
import com.drisoftie.action.async.IGenericAction;
import com.drisoftie.action.async.RegActionMethod;
import com.drisoftie.action.async.android.AndroidAction;
import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.domain.CwUser;
import com.drisoftie.cwdroid.util.CredentialUtils;
import com.drisoftie.frags.comp.FragPageMenu;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Alexander Dridiger
 */
public class FragBoard extends FragPageMenu {

    /*-###########
     * UI elements
     * ###########*/

    private SwipeRefreshLayout swipeBoard;
    private WebView            webBoard;

    /*-#######
     * Actions
     * #######*/

    private BaseAsyncAction<View, Void, Void, Void, Void> actionInit;
    private BaseAsyncAction<View, Void, Void, Void, Void> actionRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        swipeBoard = (SwipeRefreshLayout) inflater.inflate(R.layout.frag_board, container, false);
        webBoard = (WebView) swipeBoard.findViewById(R.id.web_board);
        webBoard.requestFocus(View.FOCUS_DOWN);

        webBoard.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                getArguments().putString(FragBoard.class.getName(), url);
                return true;
            }
        });

        return webBoard;
    }

    @Override
    public void onResuming() {
    }

    @Override
    public void registerComponents() {
        initActions();
        actionInit.invokeSelf();
    }

    /**
     * Most actions are created here.
     */
    @SuppressWarnings("unchecked")
    private void initActions() {
        actionInit = new ActionBuilder().with().reg(IGenericAction.class, RegActionMethod.NONE).pack(
                new AndroidAction<View, Void, Void, Void, Void>(null, null) {
                    @Override
                    public Object onActionPrepare(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                        webBoard.loadUrl(getArguments().getString(FragBoard.class.getName()));

                        webBoard.getSettings().setUseWideViewPort(false);
                        webBoard.getSettings().setJavaScriptEnabled(true);

                        CookieManager cm = CookieManager.getInstance();
                        if (!CwApp.domain().isUserLoggedIn()) {
                            cm.removeAllCookie();
                        } else {
                            CwUser user = CwApp.domain().getLoggedInUser();
                            String cookie = cm.getCookie(getString(R.string.api_domain));
                            if (StringUtils.contains(cookie, getString(R.string.cw_cookie_userid)) && StringUtils.contains(cookie,
                                                                                                                           getString(
                                                                                                                                   R.string.cw_cookie_userid) +
                                                                                                                           "=" +
                                                                                                                           user.getId()) &&
                                cookie.contains(getString(R.string.cw_cookie_pw) + "=" +
                                                CredentialUtils.deobfuscateFromBase64(user.getKeyData(), user.getPwHash()))) {
                            } else {
                                cm.removeAllCookie();
                                cm.setCookie(getString(R.string.api_domain), getString(R.string.cw_cookie_userid) +
                                                                             "=" +
                                                                             user.getId());
                                cm.setCookie(getString(R.string.api_domain), getString(R.string.cw_cookie_pw) +
                                                                             "=" +
                                                                             CredentialUtils.deobfuscateFromBase64(user.getKeyData(),
                                                                                                                   user.getPwHash()));
                            }
                        }
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

        actionRefresh = new ActionBuilder().with(swipeBoard).reg(SwipeRefreshLayout.OnRefreshListener.class, "setOnRefreshListener").reg(
                IGenericAction.class, RegActionMethod.NONE).pack(new AndroidAction<View, Void, Void, Void, Void>(null, null) {
            @Override
            public Object onActionPrepare(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                webBoard.loadUrl(getArguments().getString(FragShoutbox.class.getName()));
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
    }

    @Override
    public void onChangeOccurred(Object... args) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                actionRefresh.invokeSelf();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void deregisterComponents() {
    }

    @Override
    public void onPausing() {
    }

    @Override
    public boolean shouldClearMenu() {
        return true;
    }

    @Override
    public boolean checkMenuInflation(Menu menu) {
        return true;
    }

    @Override
    public void onMenuReady() {
    }

    @Override
    public int provideMenuRes() {
        return R.menu.board;
    }

    @Override
    public boolean isVisibleMenu() {
        return true;
    }
}
