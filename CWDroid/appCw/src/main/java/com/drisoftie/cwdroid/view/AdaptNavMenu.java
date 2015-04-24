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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.domain.CwNavItem;
import com.drisoftie.cwdroid.domain.NavItemType;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Alexander Dridiger
 */
public class AdaptNavMenu extends ArrayAdapter<CwNavItem> {

    private final NavItemType[] LOGOUT = new NavItemType[]{NavItemType.NEWS, NavItemType.BLOGS, NavItemType.SETTINGS};
    private final NavItemType[] LOGIN  =
            new NavItemType[]{NavItemType.NEWS, NavItemType.BLOGS, NavItemType.MESSAGES, NavItemType.BOARD, NavItemType.SHOUTBOX,
                    NavItemType.SETTINGS};

    private List<CwNavItem> loginItems;
    private List<CwNavItem> logoutItems;

    public AdaptNavMenu(Context context, int textViewResourceId, List<CwNavItem> entities) {
        super(context, textViewResourceId, entities);
        loginItems = createItems(LOGIN);
        logoutItems = createItems(LOGOUT);
        changeLoginMenu(false);
    }

    private List<CwNavItem> createItems(NavItemType[] toCreateFrom) {
        List<CwNavItem> items = new ArrayList<>();
        for (NavItemType navItemType : toCreateFrom) {
            CwNavItem item = new CwNavItem();
            item.setItemName(getContext().getString(navItemType.getNameId()));
            item.setNavIcon(navItemType.getIconId());
            item.setNavType(navItemType);
            items.add(item);
        }
        return items;
    }

    public void changeLoginMenu(boolean loggedIn) {
        clear();
        List<CwNavItem> items;
        if (loggedIn) {
            items = loginItems;
        } else {
            items = logoutItems;
        }
        for (CwNavItem item : items) {
            add(item);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CwNavItem  item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.litem_nav_item, parent, false);
            holder = new ViewHolder();
            holder.itemName = (TextView) viewItem.findViewById(R.id.txt_litem_nav_draw_item_name);
            holder.itemIcon = (ImageView) viewItem.findViewById(R.id.img_litem_nav_draw_item_icon);
            convertView = viewItem;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemName.setText(item.getItemName());
        holder.itemIcon.setImageResource(item.getNavIcon());

        return convertView;
    }

    private class ViewHolder {
        TextView  itemName;
        ImageView itemIcon;
    }
}
