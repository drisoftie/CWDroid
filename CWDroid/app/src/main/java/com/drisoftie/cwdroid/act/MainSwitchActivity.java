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
package com.drisoftie.cwdroid.act;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;

import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.frag.comp.FragMainPaging;
import com.drisoftie.frags.comp.FragManaged;
import com.drisoftie.frags.comp.ManagedNotifyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dridiger
 */
public class MainSwitchActivity extends ManagedNotifyActivity {

    /*-#######
     * Actions
     * #######*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initActions();

        if (CwApp.isScreenBig()) {
            setContentView(R.layout.frag_all);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                ((SlidingPaneLayout) findViewById(R.id.slide_bags)).openPane();
            }
        } else {
            if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, new FragMainPaging()).commit();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResuming() {
    }

    @Override
    protected void registerComponents() {
    }

    @Override
    protected void deregisterComponents() {
    }

    @Override
    protected void onPausing() {
    }

    private void initActions() {
    }

    @Override
    public List<FragManaged> getFragments() {
        List<FragManaged> frags = new ArrayList<FragManaged>();
        frags.add((FragManaged) getSupportFragmentManager().findFragmentById(R.id.frag_choose_items));
        frags.add((FragManaged) getSupportFragmentManager().findFragmentById(R.id.frag_travel_items));
        frags.add((FragManaged) getSupportFragmentManager().findFragmentById(R.id.frag_travel_list));
        return frags;
    }
}