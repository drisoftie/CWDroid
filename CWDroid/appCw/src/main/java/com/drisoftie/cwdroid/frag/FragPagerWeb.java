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

import android.support.v4.app.FragmentManager;

import com.drisoftie.cwdroid.R;
import com.drisoftie.frags.INotificationForwarder;
import com.drisoftie.frags.comp.BaseAdaptFragPager;
import com.drisoftie.frags.comp.FragManagedPaging;


/**
 * @author Alexander Dridiger
 */
public class FragPagerWeb extends FragManagedPaging {

    @Override
    public void onResuming() {
    }

    @Override
    public void registerComponents() {
        setPage(getArguments().getInt(getClass().getName()));
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
        return new AdaptWebPager(childFragmentManager);
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
