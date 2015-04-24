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

import java.util.List;


/**
 * Holds domain instances to centralize changes to the domain model.
 *
 * @author Alexander Dridiger
 */
public interface IDomainHolder<E extends BaseEntity> {

    /**
     * @return
     */
    List<E> getEntities();

    /**
     * @param entity
     */
    void addEntity(E entity);

    /**
     * @param entity
     */
    void removeEntity(E entity);

    /**
     * @return
     */
    IDomainLoader<E> provideDomainLoader();

    /**
     * @return
     */
    boolean isLoaded();

    /**
     * @author Alexander Dridiger
     */
    public interface IDomainLoader<E extends BaseEntity> {

        /**
         * @param entities
         */
        void loadDomain(List<E> entities);
    }
}
