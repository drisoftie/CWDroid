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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.domain.CwNews;
import com.drisoftie.cwdroid.view.AdaptNews;
import com.drisoftie.frags.comp.FragPage;

import java.util.ArrayList;

/**
 * @author Alexander Dridiger
 */
public class FragMessages extends FragPage {

    /*-###########
     * UI elements
     * ###########*/

    private ListView listBlogs;
    private AdaptNews adapterBlogs;

    /*-#######
     * Actions
     * #######*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.frag_news, container, false);
        listBlogs = (ListView) result.findViewById(R.id.list_news);
        adapterBlogs = new AdaptNews(getActivity(), R.id.txt_litem_nav_draw_item_name, new ArrayList<CwNews>());
        listBlogs.setAdapter(adapterBlogs);

        listBlogs.setEmptyView(result.findViewById(R.id.prog_list_empty_blogs));
        return result;
    }

    @Override
    public void onResuming() {
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
