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
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.domain.CwBlog;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @author Alexander Dridiger
 */
public class AdaptBlogs extends ArrayAdapter<CwBlog> {

    public AdaptBlogs(Context context, int textViewResourceId, List<CwBlog> entities) {
        super(context, textViewResourceId, entities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CwBlog     blog = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.litem_blog, parent, false);
            holder = new ViewHolder();
            holder.userIcon = (ImageView) viewItem.findViewById(R.id.img_litem_blog_user_icon);
            holder.title = (TextView) viewItem.findViewById(R.id.txt_litem_blog_title);
            holder.date = (TextView) viewItem.findViewById(R.id.txt_litem_blog_date);
            holder.cmtAmount = (TextView) viewItem.findViewById(R.id.txt_litem_blog_comments_amount);
            holder.author = (TextView) viewItem.findViewById(R.id.txt_litem_blog_author);
            holder.rating = (RatingBar) viewItem.findViewById(R.id.rate_litem_blog);
            convertView = viewItem;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(parent.getContext().getString(R.string.userpic_url, blog.getUid(), 40), holder.userIcon);
        holder.title.setText(blog.getTitle());
        holder.date.setText(DateUtils.getRelativeTimeSpanString(blog.getUnixtime() * 1000L));
        holder.cmtAmount.setText(String.valueOf(blog.getCommentsAmount()));
        holder.author.setText(blog.getAuthor());
        if (blog.getRating() > 0) {
            holder.rating.setVisibility(View.VISIBLE);
            holder.rating.setRating(blog.getRating());
        } else {
            holder.rating.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView userIcon;
        TextView  title;
        TextView  date;
        TextView  cmtAmount;
        TextView  author;
        RatingBar rating;
    }
}
