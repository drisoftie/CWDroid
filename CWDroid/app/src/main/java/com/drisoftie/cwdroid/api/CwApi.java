package com.drisoftie.cwdroid.api;

/*
 * Copyright [2009] Dimitrios Kapanikis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.R;
import com.drisoftie.cwdroid.domain.CwBlog;
import com.drisoftie.cwdroid.util.CredentialUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author Alexander Dridiger
 */
public class CwApi {

    private static String apiKey;

    private static CwApiParser<XmlUser> userParser;
    private static CwApiParser<XmlBlogs> blogsParser;

    /**
     * @return given key for talking to the API
     */
    private static String getApiKey() {
        if (apiKey == null) {
            apiKey = CwApp.string(R.string.api_key);
        }
        return apiKey;
    }

    public static XmlRoot checkApiToken() {
        XmlRoot result = null;
        CwApiParser parser = new CwApiParser<>(XmlRoot.class);
        try {
            result = (XmlRoot) parser.build().setCommand(CwApiCommand.CHECK_API_TOKEN).addArgument(
                    CwApiArgument.APITOKEN.getName(), CwApi.getApiKey()).finish().parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static AuthenticatedUser authenticate(String username, String password, boolean toHash) {
        AuthenticatedUser result = null;
        if (userParser == null) {
            userParser = new CwApiParser<>(XmlUser.class);
        }
        if (toHash) {
            password = CredentialUtils.md5(password);
        }
        try {
            XmlUser root = (XmlUser) userParser.build().setCommand(CwApiCommand.AUTHENTICATE).addArgument(
                    CwApiArgument.APITOKEN.getName(), CwApi.getApiKey()).addArgument(
                    CwApiArgument.USERNAME.getName(), username).addArgument(
                    CwApiArgument.PASSWORD.getName(), password).finish().parse();
            if (root != null && root.getUser() != null) {
                result = root.getUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<CwBlog> getBlogsList(int count) {
        return getBlogsList(null, count, null);
    }

    public static List<CwBlog> getBlogsList(int count, Integer filter) {
        return getBlogsList(null, count, filter);
    }

    public static List<CwBlog> getBlogsList(Integer uid, int count, Integer filter) {
        List<CwBlog> result = null;
        if (blogsParser == null) {
            blogsParser = new CwApiParser<>(XmlBlogs.class);
        }
        try {
            CwApiParser<XmlBlogs>.Builder builder = blogsParser.build().setCommand(CwApiCommand.GET_BLOGS_LIST).addArgument(
                    CwApiArgument.APITOKEN.getName(), CwApi.getApiKey());
            if (uid != null) {
                builder.addArgument(CwApiArgument.UID.getName(), String.valueOf(uid));
            }
            builder.addArgument(CwApiArgument.COUNT.getName(), String.valueOf(count));
            if (filter != null) {
                builder.addArgument(CwApiArgument.FILTER.getName(), String.valueOf(filter));
            }
            XmlBlogs root = (XmlBlogs) builder.finish().parse();
            if (root != null && !root.getBlogs().isEmpty()) {
                result = root.getBlogs();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //    public static final int BLANK_ARGUMENT = -1;
    //
    //
    //
    //    // minimum checkInterval
    //    long minInterval = 1 * DateUtil.MINUTE;
    //
    //    /**
    //     * @param uid    user id (only if you want blogs of this user only)
    //     * @param count  maximum number of blogs to return
    //     * @param filter see the other getBlogsList
    //     * @param since  returns only blogs since this date/time
    //     * @return list of blogs which are not older than <i>since</i>
    //     */
    //    public ArrayList<CwBlog> getBlogsList(int uid, int count, int filter, Date since) throws ConsolewarsAPIException {
    //        if (since == null)
    //            return getBlogsList(uid, count, filter);
    //        else {
    //            return (ArrayList<CwBlog>) filterBlogsListByDate(getBlogsList(uid, count, filter), since);
    //        }
    //    }
    //
    //    /**
    //     * get one blog
    //     *
    //     * @param id blog ide
    //     * @return one single blog; null if no blog is available
    //     */
    //    public CwBlog getBlog(int id) throws ConsolewarsAPIException {
    //        ArrayList<CwBlog> blogs = this.getBlogs(new int[]{id});
    //        return (blogs.isEmpty()) ? (null) : (blogs.get(0));
    //    }
    //
    //    /**
    //     * get several blogs
    //     *
    //     * @param id ids of the blogs
    //     * @return several blogs as requested by their ids
    //     */
    //    public ArrayList<CwBlog> getBlogs(int[] id) throws ConsolewarsAPIException {
    //        ArrayList<CwBlog> blogs = new ArrayList<CwBlog>();
    //
    //        // name of the api-php file
    //        String apiname = "getblogs";
    //
    //        // creating the api url for retrieving blog summaries
    //        URLCreator blogsURL = new URLCreator(BASEURL + apiname + ".php");
    //
    //        blogsURL.addArgument("apitoken", APIKey);
    //        blogsURL.addArgument("id", id);
    //
    //        SAXCwBlogParser parser = new SAXCwBlogParser(blogsURL.toString());
    //        blogs = parser.parseDocument();
    //        parser = null;
    //
    //        return blogs;
    //    }
    //
    //    /**
    //     * get comments of news or userblogs
    //     *
    //     * @param id
    //     * @param area
    //     * @param count
    //     * @param talkback_viewpage
    //     * @param talkback_lastpage
    //     * @return list of comments
    //     */
    //    public ArrayList<CwComment> getComments(int id, int area, int count, int talkback_viewpage,
    //                                            int talkback_lastpage) throws ConsolewarsAPIException {
    //
    //        ArrayList<CwComment> comments = new ArrayList<CwComment>();
    //
    //        // name of the api-php file
    //        String apiname = "getcomments";
    //
    //        URLCreator commentsURL = new URLCreator(BASEURL + apiname + ".php");
    //
    //        commentsURL.addArgument("apitoken", APIKey);
    //        commentsURL.addArgument("id", id);
    //        commentsURL.addArgument("area", area);
    //        commentsURL.addArgument("count", count);
    //        commentsURL.addArgument("talkback_viewpage", talkback_viewpage);
    //        if (talkback_lastpage != BLANK_ARGUMENT) {
    //            commentsURL.addArgument("talkback_lastpage", talkback_lastpage);
    //        }
    //
    //        SAXCommentParser parser = new SAXCommentParser(commentsURL.toString());
    //        comments = parser.parseDocument();
    //
    //        parser = null;
    //
    //        return comments;
    //    }
    //
    //    /**
    //     * @param uid user-id of the
    //     * @return the requested private messages
    //     */
    //    public ArrayList<Message> getMessages(int uid, String pass, int folder, int count) throws ConsolewarsAPIException {
    //        ArrayList<Message> msgs = new ArrayList<Message>();
    //
    //        // name of the api-php file
    //        String apiname = "getmessages";
    //
    //        URLCreator messageURL = new URLCreator(BASEURL + apiname + ".php");
    //
    //        messageURL.addArgument("apitoken", APIKey);
    //        messageURL.addArgument("user", uid);
    //        messageURL.addArgument("pass", pass);
    //        messageURL.addArgument("folder", folder);
    //        messageURL.addArgument("count", count);
    //
    //        SAXMessageParser parser = new SAXMessageParser(messageURL.toString(), uid, pass);
    //        msgs = parser.parseDocument();
    //
    //        parser = null;
    //
    //        return msgs;
    //    }
    //
    //    /**
    //     * Delivers a list of the latest news.
    //     *
    //     * @param count  the amount of news
    //     * @param filter optional filter. Current filter rules: 1=only Microsoftnews and "others" 2=only Nintendonews and
    //     *               "others" 3=only Sonynews and "others"
    //     * @return
    //     * @throws ConsolewarsAPIException
    //     */
    //    public ArrayList<CwNews> getNewsList(int count, int filter) throws ConsolewarsAPIException {
    //        ArrayList<CwNews> news = new ArrayList<CwNews>();
    //
    //        // name of the api-php file
    //        String apiname = "getnewslist";
    //
    //        URLCreator newslistURL = new URLCreator(BASEURL + apiname + ".php");
    //
    //        newslistURL.addArgument("apitoken", APIKey);
    //        newslistURL.addArgument("count", count);
    //        if (filter != BLANK_ARGUMENT)
    //            newslistURL.addArgument("filter", filter);
    //
    //        SAXCwNewsParser parser = new SAXCwNewsParser(newslistURL.toString());
    //
    //        news = parser.parseDocument();
    //
    //        parser = null;
    //
    //        return removeNewsTeaser(news);
    //    }
    //
    //    public ArrayList<CwNews> getNewsList(int count, int filter, Date since) throws ConsolewarsAPIException {
    //        if (since == null)
    //            return getNewsList(count, filter);
    //        else {
    //            return (ArrayList<CwNews>) filterNewsListByDate(getNewsList(count, filter), since);
    //        }
    //    }
    //
    //    /**
    //     * get news by id
    //     *
    //     * @param id
    //     * @return a list of news
    //     */
    //    public ArrayList<CwNews> getNews(int[] id) throws ConsolewarsAPIException {
    //        ArrayList<CwNews> news = new ArrayList<CwNews>();
    //
    //        // name of the api-php file
    //        String apiname = "getnews";
    //
    //        URLCreator newslistURL = new URLCreator(BASEURL + apiname + ".php");
    //
    //        newslistURL.addArgument("apitoken", APIKey);
    //        newslistURL.addArgument("id", id);
    //
    //        SAXCwNewsParser parser = new SAXCwNewsParser(newslistURL.toString());
    //        news = parser.parseDocument();
    //
    //        parser = null;
    //
    //        return news;
    //    }
    //
    //    /**
    //     * get a single news
    //     *
    //     * @param id news-id
    //     * @return a single news; null if no news is available
    //     */
    //    public CwNews getNews(int id) throws ConsolewarsAPIException {
    //        ArrayList<CwNews> news = getNews(new int[]{id});
    //        return (news.isEmpty()) ? (null) : (news.get(0));
    //    }
    //
    //    /**
    //     * get event messages when new blogs has been received
    //     *
    //     * @param l update listener
    //     */
    //    public void addBlogUpdateListener(CwBlogUpdateListener l) {
    //        listeners.add(l);
    //        if (bloglistUpdateThread == null) {
    //            bloglistUpdateThread = new CheckCwBlogThread(minInterval);
    //            bloglistUpdateThread.start();
    //        }
    //    }
    //
    //    /**
    //     * get event messages when new news entries has been received
    //     *
    //     * @param l update listener
    //     */
    //    public void addNewsUpdateListener(CwNewsUpdateListener l) {
    //        listeners.add(l);
    //        if (newslistUpdateThread == null) {
    //            newslistUpdateThread = new CheckCwNewsThread(minInterval);
    //            newslistUpdateThread.start();
    //        }
    //    }
    //
    //    /**
    //     * removing the newsteaser to get the normal news only
    //     *
    //     * @param news
    //     * @return
    //     */
    //    private ArrayList<CwNews> removeNewsTeaser(ArrayList<CwNews> news) {
    //        // usually the teaser is the first item in the list
    //        if (news.size() > 0) {
    //            if (news.get(0).getMode().equals("TEASER")) {
    //                news.remove(0);
    //                return news;
    //            }
    //        }
    //        // if it was not in first place the whole list will be checked
    //        for (int i = news.size() - 1; i >= 0; i--) {
    //            if (news.get(i).getMode().equals("TEASER"))
    //                news.remove(i);
    //        }
    //        return news;
    //    }
    //
    //    /**
    //     * creates a md5-hashed password
    //     *
    //     * @param password plaintext password
    //     * @return md5-hashed password
    //     */
    //    private String hashPassword(String password) {
    //        String passwordHash = "";
    //        try {
    //            MessageDigest md5 = MessageDigest.getInstance("MD5");
    //            md5.update(password.getBytes());
    //            BigInteger hash = new BigInteger(1, md5.digest());
    //            passwordHash = hash.toString(16);
    //
    //            if (passwordHash.length() == 31) {
    //                passwordHash = "0" + passwordHash;
    //            }
    //        } catch (NoSuchAlgorithmException e) {
    //            e.printStackTrace();
    //        }
    //        return passwordHash;
    //    }
    //
    //    /**
    //     * filter list by date
    //     *
    //     * @param list  original list
    //     * @param since list items should not be older than this time
    //     * @return filtered list by date
    //     */
    //    private ArrayList<CwNews> filterNewsListByDate(ArrayList<CwNews> list, Date since) {
    //        ArrayList<CwNews> filteredList = new ArrayList<CwNews>();
    //        for (CwNews news : list) {
    //            long sinceTime = since.getTime() / DateUtil.SECOND;
    //            long itemTime = news.getUnixtime();
    //            // if time of the item is after the requested "sinceTime"
    //            if (itemTime >= sinceTime) {
    //                filteredList.add(news);
    //            }
    //        }
    //        return filteredList;
    //    }
    //
    //    /**
    //     * filter list by date
    //     *
    //     * @param list  original list
    //     * @param since list items should not be older than this time
    //     * @return filtered list by date
    //     */
    //    private ArrayList<CwBlog> filterBlogsListByDate(ArrayList<CwBlog> list, Date since) {
    //        ArrayList<CwBlog> filteredList = new ArrayList<CwBlog>();
    //        for (CwBlog item : list) {
    //            long sinceTime = since.getTime() / DateUtil.SECOND;
    //            long itemTime = item.getUnixtime();
    //            // if time of the item is after the requested "sinceTime"
    //            if (itemTime >= sinceTime) {
    //                filteredList.add(item);
    //            }
    //        }
    //        return filteredList;
    //    }
    //
    //        /**
    //         * comparing the lists of the last two updates and removing double entries
    //         *
    //         * @throws ConsolewarsAPIException
    //         */
    //        protected ArrayList<T> checkUpdateByComparing() throws ConsolewarsAPIException {
    //            // updating again
    //            ArrayList<T> currentItemUpdate = getItemList();
    //
    //            ArrayList<T> cleanItemUpdate = new ArrayList<T>();
    //
    //            if (lastItemUpdate == null) {
    //                lastItemUpdate = currentItemUpdate;
    //                return currentItemUpdate;
    //            }
    //
    //            // comparing current update with the last one
    //            for (T currentUpdateItem : currentItemUpdate) {
    //                if (!contains(lastItemUpdate, currentUpdateItem)) {
    //                    cleanItemUpdate.add(currentUpdateItem);
    //                }
    //            }
    //
    //            lastItemUpdate = currentItemUpdate;
    //
    //            return cleanItemUpdate;
    //        }
    //
    //    private class CheckCwBlogThread extends APIThread<CwBlog> {
    //
    //        public CheckCwBlogThread(long checkInterval) {
    //            super(checkInterval);
    //        }
    //
    //        /**
    //         * checking if there are new news entries since the last update Note: since consolewars allows to set the time
    //         * by yourself this method is not safe to receive ALL blogs therefore implemented but not used anywhere
    //         */
    //        @SuppressWarnings("unused")
    //        private ArrayList<CwBlog> checkUpdateByDate() throws ConsolewarsAPIException {
    //            return getBlogsList(BLANK_ARGUMENT, getItemCount(), BLANK_ARGUMENT, getLastUpdate());
    //        }
    //
    //        @Override
    //        protected void checkUpdate() throws ConsolewarsAPIException {
    //            if (DEBUG)
    //                System.out.println(new Date() + ": checking for new blogs");
    //
    //            ArrayList<CwBlog> blogs = checkUpdateByComparing();
    //
    //            // ArrayList<Blog> blogs = checkUpdateByDate();
    //            if (blogs.size() > 0)
    //                fireBlogReceivedEvent(new ListEventObject<CwBlog>(this, blogs));
    //        }
    //
    //        @Override
    //        protected ArrayList<CwBlog> getItemList() throws ConsolewarsAPIException {
    //            return getBlogsList(BLANK_ARGUMENT, getItemCount(), BLANK_ARGUMENT);
    //        }
    //
    //        @Override
    //        protected boolean contains(ArrayList<CwBlog> a, CwBlog item) {
    //
    //            for (int i = 0; i < a.size(); i++) {
    //                if (a.get(i).equals(item)) {
    //                    return true;
    //                }
    //            }
    //            return false;
    //        }
    //
    //    }
    //
    //    private class CheckCwNewsThread extends APIThread<CwNews> {
    //
    //        public CheckCwNewsThread(long checkInterval) {
    //            super(checkInterval);
    //        }
    //
    //        /**
    //         * checking if there are new news entries since the last update Note: since consolewars allows to set the time
    //         * by yourself this method is not safe to receive ALL news therefore implemented but not used anywhere
    //         *
    //         * @throws ConsolewarsAPIException
    //         */
    //        @SuppressWarnings("unused")
    //        private void checkUpdateByDate() throws ConsolewarsAPIException {
    //            ArrayList<CwNews> news = getNewsList(getItemCount(), BLANK_ARGUMENT, getLastUpdate());
    //            if (news.size() > 0)
    //                fireNewsReceivedEvent(new ListEventObject<CwNews>(this, news));
    //        }
    //
    //        @Override
    //        protected void checkUpdate() throws ConsolewarsAPIException {
    //            if (DEBUG)
    //                System.out.println(new Date() + ": checking for news");
    //
    //            ArrayList<CwNews> news = checkUpdateByComparing();
    //
    //            // ArrayList<News> news = checkUpdateByDate();
    //            if (news.size() > 0)
    //                fireNewsReceivedEvent(new ListEventObject<CwNews>(this, news));
    //
    //        }
    //
    //        @Override
    //        protected ArrayList<CwNews> getItemList() throws ConsolewarsAPIException {
    //            return getNewsList(getItemCount(), BLANK_ARGUMENT);
    //        }
    //
    //        @Override
    //        protected boolean contains(ArrayList<CwNews> a, CwNews item) {
    //
    //            for (int i = 0; i < a.size(); i++) {
    //                if (a.get(i).equals(item)) {
    //                    return true;
    //                }
    //            }
    //            return false;
    //        }
    //
    //    }
}