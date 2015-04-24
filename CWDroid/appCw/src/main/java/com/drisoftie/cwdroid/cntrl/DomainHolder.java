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
package com.drisoftie.cwdroid.cntrl;

import com.drisoftie.cwdroid.domain.BaseEntity;
import com.drisoftie.cwdroid.domain.CwBlog;
import com.drisoftie.cwdroid.domain.CwMessage;
import com.drisoftie.cwdroid.domain.CwNews;
import com.drisoftie.cwdroid.domain.CwUser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;


/**
 * @author Alexander Dridiger
 */
public class DomainHolder {

    private BaseDomainHolder<CwNews>    newsHolder  = new BaseDomainHolder<>();
    private BaseDomainHolder<CwBlog>    blogsHolder = new BaseDomainHolder<>();
    private BaseDomainHolder<CwMessage> msgsHolder  = new BaseDomainHolder<>();

    private MutablePair<CwUser, Boolean> loggedInUser = new MutablePair<>(null, false);

    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> List<T> getEntities(Class<T> clazz) {
        if (CwNews.class.equals(clazz)) {
            return (List<T>) newsHolder.getEntities();
        } else if (CwBlog.class.equals(clazz)) {
            return (List<T>) blogsHolder.getEntities();
        } else if (CwMessage.class.equals(clazz)) {
            return (List<T>) blogsHolder.getEntities();
        }
        return null;
    }

    public <T extends BaseEntity> void addEntity(T entity, Class<T> clazz) {
        if (CwNews.class.equals(clazz)) {
            newsHolder.addEntity((CwNews) entity);
        } else if (CwBlog.class.equals(clazz)) {
            blogsHolder.addEntity((CwBlog) entity);
        } else if (CwMessage.class.equals(clazz)) {
            msgsHolder.addEntity((CwMessage) entity);
        }
    }

    public <T extends BaseEntity> void removeEntity(T entity, Class<T> clazz) {
        if (CwNews.class.equals(clazz)) {
            newsHolder.removeEntity((CwNews) entity);
        } else if (CwBlog.class.equals(clazz)) {
            blogsHolder.removeEntity((CwBlog) entity);
        } else if (CwMessage.class.equals(clazz)) {
            msgsHolder.removeEntity((CwMessage) entity);
        }
    }

    public boolean isLoaded(Class<? extends BaseEntity> clazz) {
        if (CwNews.class.equals(clazz)) {
            return newsHolder.isLoaded();
        } else if (CwBlog.class.equals(clazz)) {
            return blogsHolder.isLoaded();
        } else if (CwMessage.class.equals(clazz)) {
            return msgsHolder.isLoaded();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> IDomainHolder.IDomainLoader<T> provideDomainLoader(Class<T> clazz) {
        if (CwNews.class.equals(clazz)) {
            return (IDomainHolder.IDomainLoader<T>) newsHolder.provideDomainLoader();
        } else if (CwBlog.class.equals(clazz)) {
            return (IDomainHolder.IDomainLoader<T>) blogsHolder.provideDomainLoader();
        } else if (CwMessage.class.equals(clazz)) {
            return (IDomainHolder.IDomainLoader<T>) msgsHolder.provideDomainLoader();
        }
        return null;
    }

    public boolean hasUser() {
        return loggedInUser.getKey() != null;
    }

    public boolean hasValidUser() {
        return loggedInUser.getKey() != null && StringUtils.isNoneBlank(loggedInUser.getKey().getName(),
                                                                        loggedInUser.getKey().getPassword(),
                                                                        loggedInUser.getKey().getPwHash());
    }

    public boolean isUserLoggedIn() {
        return loggedInUser.getValue();
    }

    public CwUser getLoggedInUser() {
        return loggedInUser.getKey();
    }

    public void setLoggedInUser(CwUser user) {
        loggedInUser.setLeft(user);
    }

    public void setLoggedIn(boolean loggedIn) {
        loggedInUser.setRight(loggedIn);
    }
}
