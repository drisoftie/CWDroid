/*
 * Copyright [2015] [Alexander Dridiger - drisoftie@gmail.com]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.drisoftie.cwdroid.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drisoftie.action.async.ActionBuilder;
import com.drisoftie.action.async.AsyncAction;
import com.drisoftie.action.async.RegActionMethod;
import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.api.AuthenticatedUser;
import com.drisoftie.cwdroid.api.CwApi;
import com.drisoftie.cwdroid.domain.CwBlog;
import com.drisoftie.cwdroid.util.CredentialUtils;
import com.drisoftie.frags.comp.FragPage;

import java.util.List;

/**
 * @author Alexander Dridiger
 */
public class FragNews extends FragPage {

    /*-###########
     * UI elements
     * ###########*/

    /*-#######
     * Actions
     * #######*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_news, container, false);
    }

    @Override
    public void onResuming() {
        new ActionBuilder<>().with(findViewById(R.id.btn_travel_bags_add)).reg(View.OnClickListener.class, RegActionMethod.SET_ONCLICK)
                             .pack(new AsyncAction<View, Object, Object, Object>(null, null) {

                                 @Override
                                 public Object onActionPrepare(String methodName, Object[] methodArgs, Object tag1, Object tag2,
                                                               Object[] additionalTags) {
                                     return null;
                                 }

                                 @Override
                                 public Object onActionDoWork(String methodName, Object[] methodArgs, Object tag1, Object tag2,
                                                              Object[] additionalTags) {
                                     AuthenticatedUser result = CwApi.authenticate("einschnaehkeee", "finalfight", true);
                                     List<CwBlog>      blogs  = CwApi.getBlogsList(10);
                                     byte[]            key    = CredentialUtils.generateKeyBytes();
                                     String            obf    = CredentialUtils.obfuscateToBase64(key, result.getPasswordHash());
                                     String            pw     = CredentialUtils.deobfuscateFromBase64(key, obf);
                                     getClass();
                                     return null;
                                 }

                                 @Override
                                 public void onActionAfterWork(String methodName, Object[] methodArgs, Object workResult, Object tag1,
                                                               Object tag2, Object[] additionalTags) {

                                 }
                             });
    }

    @Override
    public void registerComponents() {
    }

    @Override
    public void onChangeOccurred(Object... args) {
    }

    @Override
    public void deregisterComponents() {
    }

    @Override
    public void onPausing() {
    }
}
