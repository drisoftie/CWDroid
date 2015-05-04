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
import android.support.v4.app.FragmentManager;

import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.R;
import com.drisoftie.frags.comp.BaseAdaptFragPager;
import com.drisoftie.frags.comp.FragManaged;


/**
 * A {@link BaseAdaptFragPager} for the {@link FragPagerNews}.
 *
 * @author Alexander Dridiger
 */
public class AdaptWebPager extends BaseAdaptFragPager {

    public AdaptWebPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getInitFragPageCount() {
        return 2;
    }

    @Override
    public FragManaged initFragment(int position) {
        FragManaged f    = null;
        Bundle      args = new Bundle();
        switch (position) {
            case 0:
                args.putString(FragBoard.class.getName(), CwApp.inst().getString(R.string.cw_messageboard_mobile_url));
                f = new FragBoard();
                f.setArguments(args);
                break;
            case 1:
                args.putString(FragShoutbox.class.getName(), CwApp.inst().getString(R.string.cw_shoutbox));
                f = new FragShoutbox();
                f.setArguments(args);
                break;
        }
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int title = -1;
        switch (position) {
            case 0:
                title = R.string.page_board;
                break;
            case 1:
                title = R.string.page_shoutbox;
                break;
        }
        return CwApp.inst().getString(title);
    }

    @Override
    public int getIndexFor(FragManaged fragment) {
        int index = -1;
        return index;
    }
}