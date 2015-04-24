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

package com.drisoftie.cwdroid.api;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Alexander Dridiger
 */
@Root(name = "item", strict = false)
public class AuthenticatedUser {

    @Element(name = "success", required = false)
    private UserStatus success;

    @Element(name = "user", required = false)
    private String username;

    @Element(name = "uid", required = false)
    private int uid;

    @Element(name = "passwordhash", required = false)
    private String passwordHash;

    @Element(name = "reason", required = false)
    private String reason;

    public UserStatus getSuccess() {
        return success;
    }

    public void setSuccess(UserStatus success) {
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
