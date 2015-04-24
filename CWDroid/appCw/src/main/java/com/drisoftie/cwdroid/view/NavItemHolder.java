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

package com.drisoftie.cwdroid.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.domain.CwNavItem;

/**
 * @author Alexander Dridiger
 */
public class NavItemHolder extends RecyclerView.ViewHolder {

    TextView  itemName;
    ImageView itemIcon;

    public NavItemHolder(View itemView) {
        super(itemView);
        itemName = (TextView) itemView.findViewById(R.id.txt_litem_nav_draw_item_name);
        itemIcon = (ImageView) itemView.findViewById(R.id.img_litem_nav_draw_item_icon);
    }

    public void bindItem(CwNavItem item) {
    }
}