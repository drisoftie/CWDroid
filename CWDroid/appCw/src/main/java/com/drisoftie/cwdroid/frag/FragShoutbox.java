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
import com.drisoftie.cwdroid.domain.CwNews;
import com.drisoftie.cwdroid.domain.CwUser;
import com.drisoftie.cwdroid.util.CredentialUtils;
import com.drisoftie.frags.comp.FragPageMenu;

import java.util.List;

/**
 * @author Alexander Dridiger
 */
public class FragShoutbox extends FragPageMenu {

     /*-###########
     * UI elements
     * ###########*/

    private SwipeRefreshLayout swipeShoutbox;
    private WebView            webShoutbox;

    /*-#######
     * Actions
     * #######*/

    private BaseAsyncAction<View, Void, Void, Void, Void> actionInit;
    private BaseAsyncAction<View, Void, Void, Void, Void> actionRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        swipeShoutbox = (SwipeRefreshLayout) inflater.inflate(R.layout.frag_shoutbox, container, false);
        webShoutbox = (WebView) swipeShoutbox.findViewById(R.id.web_shoutbox);

        webShoutbox.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                getArguments().putString(FragShoutbox.class.getName(), url);
                return true;
            }
        });
        return webShoutbox;
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
                        webShoutbox.loadUrl(getArguments().getString(FragShoutbox.class.getName()));

                        webShoutbox.getSettings().setUseWideViewPort(false);
                        webShoutbox.getSettings().setJavaScriptEnabled(true);

                        CookieManager cm = CookieManager.getInstance();
                        cm.setAcceptCookie(true);
                        if (CwApp.domain().isUserLoggedIn()) {
                            CwUser user = CwApp.domain().getLoggedInUser();

                            cm.setCookie(getString(R.string.cw_url_slash), getString(R.string.cw_cookie_userid) + "=" + user.getId());
                            cm.setCookie(getString(R.string.cw_url_slash), getString(R.string.cw_cookie_pw) + "=" +
                                                                           CredentialUtils.deobfuscateFromBase64(user.getKeyData(),
                                                                                                                 user.getPwHash()));
                            cm.setCookie(getString(R.string.cw_url_slash), "cwbb_sessionhash" + "=" +
                                                                           "b71af138197a8c5443059316967d4");

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

        actionRefresh = new ActionBuilder().with(swipeShoutbox).reg(SwipeRefreshLayout.OnRefreshListener.class, "setOnRefreshListener").reg(
                IGenericAction.class, RegActionMethod.NONE).pack(new AndroidAction<View, List<CwNews>, List<CwNews>, Void, Void>(null,
                                                                                                                                 null) {
            @Override
            public Object onActionPrepare(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                webShoutbox.loadUrl(getArguments().getString(FragShoutbox.class.getName()));

                CookieManager cm = CookieManager.getInstance();
                cm.setAcceptCookie(true);
                if (CwApp.domain().isUserLoggedIn()) {
                    CwUser user = CwApp.domain().getLoggedInUser();

                    cm.setCookie(getString(R.string.cw_url_slash), getString(R.string.cw_cookie_userid) + "=" + user.getId());
                    cm.setCookie(getString(R.string.cw_url_slash), getString(R.string.cw_cookie_pw) + "=" +
                                                                   CredentialUtils.deobfuscateFromBase64(user.getKeyData(),
                                                                                                         user.getPwHash()));
                }
                skipWorkThreadOnce();
                return null;
            }

            @Override
            public List<CwNews> onActionDoWork(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                return null;
            }

            @Override
            public void onActionAfterWork(String methodName, Object[] methodArgs, List<CwNews> result, Void tag1, Void tag2,
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
        return R.menu.shoutbox;
    }

    @Override
    public boolean isVisibleMenu() {
        return true;
    }
}
