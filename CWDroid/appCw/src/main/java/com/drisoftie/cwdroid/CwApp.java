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
package com.drisoftie.cwdroid;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.support.annotation.StringRes;

import com.drisoftie.cwdroid.cntrl.DomainHolder;
import com.drisoftie.cwdroid.db.SqliteOpenHelper;
import com.drisoftie.cwdroid.domain.BaseEntity;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;


/**
 * Application stands as a singleton for the whole app and provides access to underlying functionality.
 *
 * @author Alexander Dridiger
 */
public class CwApp extends Application {

    /**
     * Singleton instance.
     */
    public static CwApp instance;

    private static Boolean isScreenBig = null;


    private DomainHolder domainHolder;

    /*-################
    * Database helpers
    * ################*/
    private SqliteOpenHelper dbHelper;

    /**
     * @return Singleton instance
     */
    public static CwApp getInstance() {
        return instance;
    }

    /**
     * Delegate method for {@link #getInstance()}
     */
    public static CwApp inst() {
        return getInstance();
    }

    /**
     * @return delegate to {@link #getString(int, Object...)}
     */
    public static String string(@StringRes int resId, Object... args) {
        return CwApp.inst().getString(resId, args);
    }

    /**
     * @return delegate to {@link #getResources()}
     */
    public static Resources res() {
        return CwApp.inst().getResources();
    }

    /**
     * Return the right {@link com.j256.ormlite.dao.Dao} for the given {@link java.lang.Class}
     *
     * @param clazz the given class
     * @param <T>   type of the given class
     * @return the generated dao
     */
    public static <T extends BaseEntity> Dao dao(Class<T> clazz) {
        Dao<T, ?> result = null;
        try {
            result = DaoManager.createDao(inst().dbHelper.getConnectionSource(), clazz);
        } catch (SQLException | java.sql.SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static DomainHolder domain() {
        return CwApp.inst().domainHolder;
    }

    /**
     * Indicates if the current device has a big screen.
     *
     * @return if the device's screen is big
     */
    public static Boolean isScreenBig() {
        return isScreenBig;
    }

    @Override
    public void onCreate() {
        instance = this;

        isScreenBig = getResources().getBoolean(R.bool.isScreenBig);

        OpenHelperManager.setOpenHelperClass(SqliteOpenHelper.class);
        dbHelper = OpenHelperManager.getHelper(this, SqliteOpenHelper.class);

        domainHolder = new DomainHolder();

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).diskCache(new UnlimitedDiscCache(
                StorageUtils.getOwnCacheDirectory(this, getString(R.string.file_dir_pics)), null, new FileNameGenerator() {

            private final String extension = getString(R.string.file_extension_pics);

            @Override
            public String generate(String imageUri) {
                return String.valueOf(imageUri.hashCode()) + extension;
            }
        })).diskCacheExtraOptions(500, 500, null).memoryCacheExtraOptions(500, 500).defaultDisplayImageOptions(
                new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_mock).cacheInMemory(true).cacheOnDisk(true).build())
                                                                                                       .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onTerminate() {
        OpenHelperManager.releaseHelper();
    }

    /**
     * Checks if the device is online.
     *
     * @return if online
     */
    public boolean isDeviceOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}