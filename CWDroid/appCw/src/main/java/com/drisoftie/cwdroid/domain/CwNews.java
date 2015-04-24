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
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Transient;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Represents a persisted news and is also used as a DTO for the {@link com.drisoftie.cwdroid.api.CwApi}.
 *
 * @author Alexander Dridiger
 */
@DatabaseTable(tableName = "News")
public class CwNews extends CwSubject {

    @DatabaseField(columnName = "authorId")
    @Transient
    private int authorId;

    @DatabaseField(columnName = "category")
    @Element(name = "category", required = false)
    private String category;

    @DatabaseField(columnName = "categoryshort")
    @Element(name = "categoryshort", required = false)
    private String categoryShort;

    @DatabaseField(columnName = "picList")
    @Element(name = "piclist", required = false)
    private String picList;

    @ForeignCollectionField()
    @Transient
    private Collection<CwComment> comments;

    @ForeignCollectionField()
    @Transient
    private Collection<CwPicture> pictures;

    @ForeignCollectionField()
    @Transient
    private Collection<CwVideo> videos;

    /**
     * Mandatory
     */
    public CwNews() {
    }

    public CwNews(int authorId, String article, String author, int comments, String description, String mode, int subjectId, String title,
                  int unixtime, String url, String category, String categoryShort, String picList, Collection<CwPicture> pictures) {
        super(article, author, comments, description, mode, subjectId, title, unixtime, url);
        this.authorId = authorId;
        this.category = category;
        this.categoryShort = categoryShort;
        this.picList = picList;
        this.pictures = pictures;
    }

    /**
     * @return the authorId
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * @param authorId the authorId to set
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the categoryShort
     */
    public String getCategoryShort() {
        return categoryShort;
    }

    /**
     * @param categoryShort the categoryShort to set
     */
    public void setCategoryShort(String categoryShort) {
        this.categoryShort = categoryShort;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }

    /**
     * @return the comments
     */
    public Collection<CwComment> getComments() {
        if (comments == null) {
            comments = new ArrayList<CwComment>();
        }
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(Collection<CwComment> comments) {
        this.comments = comments;
    }

    /**
     * @return the pictures
     */
    public Collection<CwPicture> getPictures() {
        if (pictures == null) {
            pictures = new ArrayList<CwPicture>();
        }
        return pictures;
    }

    /**
     * @param pictures the pictures to set
     */
    public void setPictures(Collection<CwPicture> pictures) {
        this.pictures = pictures;
    }

    /**
     * @return the videos
     */
    public Collection<CwVideo> getVideos() {
        if (videos == null) {
            videos = new ArrayList<CwVideo>();
        }
        return videos;
    }

    /**
     * @param videos the videos to set
     */
    public void setVideos(Collection<CwVideo> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(getSubjectId()).toString();
    }
}
