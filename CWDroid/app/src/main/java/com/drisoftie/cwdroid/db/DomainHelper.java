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
package com.drisoftie.cwdroid.db;

import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.domain.BaseEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

import java.sql.SQLException;

/**
 * @author Alexander Dridiger
 */
public class DomainHelper {

    /**
     * Creates or updates data provided by the {@code this} inside the database table associated with the {@code clazz} argument. Cascading
     * method.
     *
     * @param clazz {@link Class} associated with a database table
     * @return returns the dao for the provided {@code clazz}
     */
    public static synchronized <DatabaseT extends BaseEntity> void createOrUpdate(Class<DatabaseT> clazz, DatabaseT entity) {
        //        if (ItemCategory.class.equals(clazz)) {
        //            Dao<ItemCategory, ?> dao = CheckApp.dao(ItemCategory.class);
        //            ItemCategory category = (ItemCategory) entity;
        //            createOrUpdate(dao, category);
        //            for (Item item : category.getItems()) {
        //                createOrUpdate(Item.class, item);
        //            }
        //        } else if (TravelBag.class.equals(clazz)) {
        //            Dao<TravelBag, ?> dao = CheckApp.dao(TravelBag.class);
        //            TravelBag travelList = (TravelBag) entity;
        //            createOrUpdate(dao, travelList);
        //            for (Item item : travelList.getItems()) {
        //                createOrUpdate(Item.class, item);
        //            }
        //        } else {
        //            Dao<DatabaseT, ?> dao = CheckApp.dao(clazz);
        //            createOrUpdate(dao, entity);
        //        }
    }

    /**
     * Creates or updates data provided by the {@code this} inside the database table associated with the {@code clazz} argument.
     *
     * @param clazz {@link Class} associated with a database table
     * @return returns the dao for the provided {@code clazz}
     */
    public static synchronized <DatabaseT extends BaseEntity> void createOrUpdateSimple(Class<DatabaseT> clazz, DatabaseT entity) {
        Dao<DatabaseT, ?> dao = CwApp.dao(clazz);
        createOrUpdate(dao, entity);
    }

    private static <DatabaseT extends BaseEntity> CreateOrUpdateStatus createOrUpdate(Dao<DatabaseT, ?> dao, DatabaseT entity) {
        CreateOrUpdateStatus status = null;
        // Context c = CheckApp.inst();
        try {
            // Log.v(c.getString(R.string.log_ormlite), c.getString(R.string.log_ormlite_creating_updating, entity.getClass().getName()));
            status = dao.createOrUpdate(entity);
            if (status.isCreated()) {
                // Log.v(c.getString(R.string.log_ormlite),
                // c.getResources().getQuantityString(R.plurals.log_ormlite_number_of_rows_created, status.getNumLinesChanged(),
                // status.getNumLinesChanged()));
            } else if (status.isUpdated()) {
                // Log.v(c.getString(R.string.log_ormlite), c.getString(R.string.log_ormlite_updated, status.getNumLinesChanged()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static synchronized <DatabaseT extends BaseEntity> void deleteEntity(Class<DatabaseT> clazz, DatabaseT entity) {
        Dao<DatabaseT, ?> dao = CwApp.dao(clazz);
        deleteEntity(dao, entity);
    }

    public static <DatabaseT extends BaseEntity> void deleteCascade(Class<DatabaseT> clazz, DatabaseT entity) {
        Dao<DatabaseT, ?> dao = CwApp.dao(clazz);
        deleteEntity(dao, entity);
    }

    private static <DatabaseT extends BaseEntity> void deleteEntity(Dao<DatabaseT, ?> dao, DatabaseT entity) {
        // Context c = CheckApp.inst();
        try {
            // Log.v(c.getString(R.string.nc_log_ormlite), c.getString(R.string.nc_log_ormlite_deleting, entity.getClass().getName()));
            // int rows =
            dao.delete(entity);
            // Log.v(c.getString(R.string.nc_log_ormlite),
            // c.getResources().getQuantityString(R.plurals.nc_log_ormlite_number_of_rows_deleted, rows, rows));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
