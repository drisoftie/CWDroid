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

package com.drisoftie.cwdroid.api;

import android.net.Uri;

import com.drisoftie.cwdroid.CwApp;
import com.drisoftie.cwdroid.R;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpHost;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.net.URL;


/**
 * @author Alexander Dridiger
 */
public class CwApiParser<T> {

    private Builder builder;
    private final Class<T> type;

    private Class<T> getType() {
        return this.type;
    }

    public CwApiParser(Class<T> type) {
        this.type = type;
    }

    public Builder build() {
        if (builder == null) {
            builder = new Builder();
        }
        return builder;
    }

    public T parse() throws IOException {

        String xml = IOUtils.toString(new URL(builder.builder.toString()), CharEncoding.UTF_8);

        Serializer serializer = new Persister();
        T root = null;
        try {
            root = (T) serializer.read(type, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    public class Builder {

        private Uri.Builder builder;

        protected Builder() {
            prepare(CwApp.string(R.string.api_domain), CwApp.string(R.string.api_path));
        }


        private Builder prepare(String domain, String apiPath) {
            builder = new Uri.Builder();
            builder.scheme(HttpHost.DEFAULT_SCHEME_NAME).authority(domain).appendPath(apiPath);
            return this;
        }

        public Builder setCommand(CwApiCommand command) {
            builder.appendPath(CwApp.string(R.string.api_path_end, command.getName()));
            return this;
        }

        public Builder addArgument(String name, String value) {
            builder.appendQueryParameter(name, value);
            return this;
        }

        public CwApiParser finish() {
            return CwApiParser.this;
        }
    }
}