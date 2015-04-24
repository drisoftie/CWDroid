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

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;


/**
 * Represents a persisted user. Can't be used as a DTO for the {@link com.drisoftie.cwdroid.api.CwApi}.
 *
 * @author w4yn3
 */
@DatabaseTable(tableName = "Userdata")
public class CwUser extends BaseEntity {

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] keyData;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "pwHash")
    private String pwHash;

    @DatabaseField(columnName = "date", dataType = DataType.DATE)
    private Date date;

    @DatabaseField(columnName = "lastNewsId")
    private int lastNewsId;

    @DatabaseField(columnName = "lastBlogId")
    private int lastBlogId;

    /**
     * Mandatory
     */
    public CwUser() {
    }

    public CwUser(String name, String password, Date date, int lastNewsId, int lastBlogId) {
        this.name = name;
        this.password = password;
        this.date = date;
        this.lastNewsId = lastNewsId;
        this.lastBlogId = lastBlogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public byte[] getKeyData() {
        return keyData;
    }

    public void setKeyData(byte[] keyData) {
        this.keyData = keyData;
    }

    /**
     * Last known login date.
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * Last known login date.
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public int getLastNewsId() {
        return lastNewsId;
    }

    public void setLastNewsId(int lastNewsId) {
        this.lastNewsId = lastNewsId;
    }

    public int getLastBlogId() {
        return lastBlogId;
    }

    public void setLastBlogId(int lastBlogId) {
        this.lastBlogId = lastBlogId;
    }

    public void resetCredentials() {
        setPassword("");
        setPwHash("");
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(getId()).append(name).toString();
    }
}
