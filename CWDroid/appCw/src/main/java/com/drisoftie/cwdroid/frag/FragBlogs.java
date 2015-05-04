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
package com.drisoftie.cwdroid.frag;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.drisoftie.action.async.ActionBuilder;
import com.drisoftie.action.async.BaseAsyncAction;
import com.drisoftie.action.async.IGenericAction;
import com.drisoftie.action.async.RegActionMethod;
import com.drisoftie.action.async.android.AndroidAction;
import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.api.CwApi;
import com.drisoftie.cwdroid.domain.CwBlog;
import com.drisoftie.cwdroid.view.AdaptBlogs;
import com.drisoftie.frags.comp.FragPage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dridiger
 */
public class FragBlogs extends FragPage {

    /*-###########
     * UI elements
     * ###########*/

    private ListView           listBlogs;
    private AdaptBlogs         listAdapBlogs;
    private SwipeRefreshLayout swipeList;
    private SwipeRefreshLayout swipeListEmpty;

    /*-#######
     * Actions
     * #######*/

    private BaseAsyncAction<View, List<CwBlog>, List<CwBlog>, Void, Void> actionRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.frag_blogs, container, false);
        listBlogs = (ListView) result.findViewById(R.id.list_blogs);
        listAdapBlogs = new AdaptBlogs(getActivity(), R.id.txt_litem_blog_title, new ArrayList<CwBlog>());
        listBlogs.setAdapter(listAdapBlogs);

        listBlogs.setEmptyView(result.findViewById(R.id.swipe_refresh_list_empty_blogs));

        swipeList = (SwipeRefreshLayout) result.findViewById(R.id.swipe_refresh_blogs);
        swipeList.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                                          android.R.color.holo_orange_light, android.R.color.holo_red_light);

        swipeListEmpty = (SwipeRefreshLayout) result.findViewById(R.id.swipe_refresh_list_empty_blogs);
        swipeListEmpty.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                                               android.R.color.holo_orange_light, android.R.color.holo_red_light);
        return result;
    }

    @Override
    public void onResuming() {
    }

    @Override
    public void registerComponents() {
        initActions();
    }

    /**
     * Most actions are created here.
     */
    @SuppressWarnings("unchecked")
    private void initActions() {
        actionRefresh = new ActionBuilder().with(swipeList, swipeListEmpty).reg(SwipeRefreshLayout.OnRefreshListener.class,
                                                                                "setOnRefreshListener").reg(IGenericAction.class,
                                                                                                            RegActionMethod.NONE).pack(
                new AndroidAction<View, List<CwBlog>, List<CwBlog>, Void, Void>(null, null) {
                    @Override
                    public Object onActionPrepare(String methodName, Object[] methodArgs, Void tag1, Void tag2, Object[] additionalTags) {
                        return null;
                    }

                    @Override
                    public List<CwBlog> onActionDoWork(String methodName, Object[] methodArgs, Void tag1, Void tag2,
                                                       Object[] additionalTags) {
                        List<CwBlog> blog  = new ArrayList<>();
                        int          count = 10;
                        while (blog.size() < 10) {
                            count += 10;
                            blog = CwApi.getBlogsList(count);
                            postActionProgress(blog, methodName, methodArgs);
                        }
                        return null;
                    }

                    @Override
                    public void onActionProgress(String methodName, List<CwBlog> progress, Object[] methodArgs, Void tag1, Void tag2,
                                                 Object[] additionalTags) {
                        for (CwBlog blog : progress) {
                            listAdapBlogs.add(blog);
                        }
                    }

                    @Override
                    public void onActionAfterWork(String methodName, Object[] methodArgs, List<CwBlog> result, Void tag1, Void tag2,
                                                  Object[] additionalTags) {
                        swipeList.setRefreshing(false);
                        swipeListEmpty.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onChangeOccurred(Object... args) {
    }

    @Override
    public void deregisterComponents() {
    }

    @Override
    public void onPausing() {
    }
}
