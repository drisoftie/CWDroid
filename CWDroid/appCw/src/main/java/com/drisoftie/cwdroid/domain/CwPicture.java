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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@DatabaseTable(tableName = "Picture")
public class CwPicture extends BaseEntity {

    @DatabaseField(columnName = "picId")
    private String picId;

    @DatabaseField(columnName = "url", canBeNull = false)
    private String url;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CwNews cwNews;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private CwBlog cwBlog;

    /**
     * Mandatory
     */
    public CwPicture() {
    }

    public CwPicture(String picId, String url) {
        this.picId = picId;
        this.url = url;
    }

    /**
     * @return the picId
     */
    public String getPicId() {
        return picId;
    }

    /**
     * @param picId the picId to set
     */
    public void setPicId(String picId) {
        this.picId = picId;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the cwNews
     */
    public CwNews getCwNews() {
        return cwNews;
    }

    /**
     * @param cwNews the cwNews to set
     */
    public void setCwNews(CwNews cwNews) {
        this.cwNews = cwNews;
    }

    public CwBlog getCwBlog() {
        return cwBlog;
    }

    public void setCwBlog(CwBlog cwBlog) {
        this.cwBlog = cwBlog;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(getId()).append(getUrl()).toString();
    }
}
