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

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.drisoftie.cwdroid.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;


/**
 * Central helper class for the SQLite database.
 *
 * @author Alexander Dridiger
 */
public class SqliteOpenHelper extends OrmLiteSqliteOpenHelper {

    private Context context;

    /**
     * Sets the Android {@link android.content.Context} to the helper.
     *
     * @param context the Android {@link android.content.Context} to set
     */
    public SqliteOpenHelper(Context context) {
        super(
                context, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + context.getString(
                        R.string.db_dir) + File.separator + context.getString(R.string.db_name), null, Integer.valueOf(
                        context.getResources().getInteger(R.integer.dbversion)));
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            for (String tableName : context.getResources().getStringArray(R.array.dbtables)) {
                TableUtils.createTable(connectionSource, Class.forName(context.getString(R.string.classprefix, tableName)));
            }
        } catch (SQLException e) {
            Log.e("", e.getMessage());
        } catch (java.sql.SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            for (String tableName : context.getResources().getStringArray(R.array.dbtables)) {
                TableUtils.dropTable(connectionSource, Class.forName(context.getString(R.string.classprefix, tableName)), true);
            }
            onCreate(db);
        } catch (SQLException e) {
            Log.e("", e.getMessage());
        } catch (java.sql.SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes and recreates all available database tables.
     *
     * @param connectionSource
     */
    public void deleteAll(ConnectionSource connectionSource) {
        try {
            for (String tableName : context.getResources().getStringArray(R.array.dbtables)) {
                TableUtils.dropTable(connectionSource, Class.forName(context.getString(R.string.classprefix, tableName)), true);
            }
            for (String tableName : context.getResources().getStringArray(R.array.dbtables)) {
                TableUtils.createTable(connectionSource, Class.forName(context.getString(R.string.classprefix, tableName)));
            }
        } catch (SQLException e) {
            Log.e("", e.getMessage());
        } catch (java.sql.SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}