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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author Alexander Dridiger
 */
public class BaseDomainHolder<E extends BaseEntity> implements IDomainHolder<E> {

    private List<E> entities = Collections.synchronizedList(new ArrayList<E>());

    private IDomainLoader<E> loader;

    public BaseDomainHolder() {
        loader = new IDomainLoader<E>() {
            @Override
            public void loadDomain(List<E> entities) {
                BaseDomainHolder.this.entities.clear();
                BaseDomainHolder.this.entities.addAll(entities);
            }
        };
    }

    @Override
    public List<E> getEntities() {
        List<E> result = new ArrayList<E>();
        result.addAll(entities);
        return result;
    }

    @Override
    public void addEntity(E entity) {
        entities.add(entity);
    }

    @Override
    public void removeEntity(E entity) {
        entities.remove(entity);
    }

    @Override
    public boolean isLoaded() {
        return entities != null && entities.size() > 0;
    }

    @Override
    public IDomainLoader<E> provideDomainLoader() {
        return loader;
    }
}
