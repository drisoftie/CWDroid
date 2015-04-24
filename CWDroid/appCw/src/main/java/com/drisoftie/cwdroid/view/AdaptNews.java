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
import android.widget.TextView;

import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.domain.CwNews;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @author Alexander Dridiger
 */
public class AdaptNews extends ArrayAdapter<CwNews> {

    public AdaptNews(Context context, int textViewResourceId, List<CwNews> entities) {
        super(context, textViewResourceId, entities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CwNews     news = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.litem_news, parent, false);
            holder = new ViewHolder();
            holder.categoryIcon = (ImageView) viewItem.findViewById(R.id.img_litem_news_category);
            holder.category = (TextView) viewItem.findViewById(R.id.txt_litem_news_category);
            holder.title = (TextView) viewItem.findViewById(R.id.txt_litem_news_title);
            holder.descr = (TextView) viewItem.findViewById(R.id.txt_litem_news_descr);
            holder.date = (TextView) viewItem.findViewById(R.id.txt_litem_news_date);
            holder.cmtAmount = (TextView) viewItem.findViewById(R.id.txt_litem_news_comments_amount);
            holder.author = (TextView) viewItem.findViewById(R.id.txt_litem_news_author);
            convertView = viewItem;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(parent.getContext().getString(R.string.catpic_url, news.getCategoryShort()),
                                               holder.categoryIcon);
        holder.category.setText(news.getCategory());
        holder.title.setText(news.getTitle());
        holder.descr.setText(news.getDescription());
        holder.date.setText(DateUtils.getRelativeTimeSpanString(news.getUnixtime() * 1000L, System.currentTimeMillis(),
                                                                DateUtils.DAY_IN_MILLIS));
        holder.cmtAmount.setText(String.valueOf(news.getCommentsAmount()));
        holder.author.setText(news.getAuthor());

        return convertView;
    }

    private class ViewHolder {
        ImageView categoryIcon;
        TextView  category;
        TextView  title;
        TextView  descr;
        TextView  date;
        TextView  cmtAmount;
        TextView  author;
    }
}
