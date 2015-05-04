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

package com.drisoftie.cwdroid.util;

import android.content.Context;
import android.util.Log;

import com.drisoftie.cwdroid.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author Alexander Dridiger
 */
public class HttpPoster {

    private final Context context;

    public HttpPoster(Context context) {
        this.context = context;
    }

    public void sendPost(String urlString, String cookies, String data) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(context.getString(R.string.post));

        connection.setRequestProperty(context.getString(R.string.cookie), cookies);
        connection.connect();

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(data);
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        // Get HTML from Server
        String getData = "";
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            getData += decodedString + "\n";
        }
        in.close();

        Log.i("**RESPONSEURLCONN!!**", getData);

        // return getData;
    }
}
