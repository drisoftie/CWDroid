/*****************************************************************************
 *
 * Copyright 2012-2013 Sony Corporation
 *
 * The information contained here-in is the property of Sony corporation and
 * is not to be disclosed or used without the prior written permission of
 * Sony corporation. This copyright extends to all media in which this
 * information may be preserved including magnetic storage, computer
 * print-out or visual display.
 *
 * Contains proprietary information, copyright and database rights Sony.
 * Decompilation prohibited save as permitted by law. No using, disclosing,
 * reproducing, accessing or modifying without Sony prior written consent.
 *
 ****************************************************************************/
package com.drisoftie.cwdroid.frag.comp;

import android.support.v4.app.FragmentManager;

import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.R;
import com.drisoftie.frags.comp.BaseAdaptFragPager;
import com.drisoftie.frags.comp.FragManaged;


/**
 * A {@link BaseAdaptFragPager} for the {@link FragMainPaging}.
 *
 * @author Alexander Dridiger
 */
public class AdaptMainPager extends BaseAdaptFragPager {

    public AdaptMainPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getInitFragPageCount() {
        return 2;
    }

    @Override
    public FragManaged initFragment(int position) {
        FragManaged f = null;
        switch (position) {
            case 0:
                f = new FragBag();
                break;
            case 1:
                f = new FragBag();
                break;
        }
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int title = -1;
        switch (position) {
            case 0:
                title = R.string.page_travel_bags;
                break;
            case 1:
                title = R.string.page_bag;
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