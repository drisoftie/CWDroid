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

package com.drisoftie.cwdroid.domain;

import com.drisoftie.cwdroid.R;

/**
 * @author Alexander Dridiger
 */
public enum NavItemType {

    NEWS(R.string.menu_nav_news, R.drawable.ic_nav_news),

    BLOGS(R.string.menu_nav_blogs, R.drawable.ic_nav_blogs),

    MESSAGES(R.string.menu_nav_msgs, R.drawable.ic_nav_msgs),

    BOARD(R.string.menu_nav_board, R.drawable.ic_nav_board),

    SHOUTBOX(R.string.menu_nav_shoutbox, R.drawable.ic_nav_shoutbox),

    SETTINGS(R.string.menu_nav_settings, R.drawable.ic_nav_settings);


    private final int nameId;
    private final int iconId;

    private NavItemType(int nameId, int iconId) {
        this.nameId = nameId;
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public int getNameId() {
        return nameId;
    }
}
