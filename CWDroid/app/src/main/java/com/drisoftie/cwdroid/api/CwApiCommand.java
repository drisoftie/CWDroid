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

package com.drisoftie.cwdroid.api;

/**
 * @author Alexander Dridiger
 */
public enum CwApiCommand {

    CHECK_API_TOKEN("checkapitoken"),

    AUTHENTICATE("authenticate"),

    GET_BLOGS_LIST("getblogslist"),

    GET_BLOGS("getblogs"),

    GET_COMMENTS("getcomments"),

    GET_MESSAGES("getmessages"),

    GET_NEWS_LIST("getnewslist"),

    GET_NEWS("getnews");

    private final String name;

    private CwApiCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
