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

import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.domain.CwNavItem;
import com.drisoftie.cwdroid.domain.NavItemType;
import com.drisoftie.frags.handler.IResultHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Alexander Dridiger
 */
public class AdaptRecycNavMenu extends RecyclerView.Adapter<NavItemHolder> {


    private List<CwNavItem> logoutItems;
    private List<CwNavItem> loginItems;
    private List<CwNavItem> items;

    private IResultHandler<NavItemType> handler;

    public AdaptRecycNavMenu() {
//        logoutItems = createItems(R.array.nav_items, R.array.nav_items_icons);
//        loginItems = createItems(R.array.nav_items_login, R.array.nav_items_icons_login);
        changeLoginMenu(false);
    }

    public void registerNavHandler(IResultHandler<NavItemType> handler) {

    }

    private List<CwNavItem> createItems(int nameIds, int iconIds) {
        List<CwNavItem> items           = new ArrayList<>();
        String[]        loginItems      = CwApp.inst().getResources().getStringArray(nameIds);
        TypedArray      loginItemsIcons = CwApp.inst().getResources().obtainTypedArray(iconIds);
        for (int i = 0; i < loginItems.length; i++) {
            String itemName = loginItems[i];
            int itemIcon = loginItemsIcons.getResourceId(i, 0);
            CwNavItem item = new CwNavItem();
            item.setItemName(itemName);
            item.setNavIcon(itemIcon);
            items.add(item);
        }
        loginItemsIcons.recycle();
        return items;
    }

    public void changeLoginMenu(boolean loggedIn) {
        if (loggedIn) {
            items = loginItems;
        } else {
            items = logoutItems;
        }
        notifyDataSetChanged();
    }

    @Override
    public NavItemHolder onCreateViewHolder(ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.litem_nav_item, parent, false);
        return new NavItemHolder(view);
    }

    @Override
    public void onBindViewHolder(NavItemHolder holder, int pos) {
        CwNavItem crime = items.get(pos);
        holder.bindItem(crime);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
