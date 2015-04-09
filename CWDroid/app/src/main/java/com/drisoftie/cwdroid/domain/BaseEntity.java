/*
 * Copyright [2015] [Alexander Dridiger - drisoftie@gmail.com]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.drisoftie.cwdroid.domain;

import com.j256.ormlite.field.DatabaseField;


/**
 * Basic persistance class providing an ID for the database. Used as parent for all other entities. <br>
 * Provides means to access the database in easy ways.
 * 
 * @author Alexander Dridiger
 */
public abstract class BaseEntity {

    @DatabaseField(generatedId = true)
    private Integer id;

    /**
     * Get generated database id.
     *
     * @return db id
     */
    public Integer getId() {
        return id;
    }
}
