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

import com.drisoftie.cwdroid.R;
import com.drisoftie.frags.INotificationForwarder;
import com.drisoftie.frags.comp.BaseAdaptFragPager;
import com.drisoftie.frags.comp.FragManagedPaging;


/**
 * @author Alexander Dridiger
 */
public class FragMainPaging extends FragManagedPaging {

    @Override
    public void onResuming() {
    }

    @Override
    public void registerComponents() {
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
        return new AdaptMainPager(childFragmentManager);
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
        return R.color.dark_blue;
    }
}
