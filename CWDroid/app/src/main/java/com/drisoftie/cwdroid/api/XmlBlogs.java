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

import com.drisoftie.cwdroid.domain.CwBlog;
import com.drisoftie.cwdroid.domain.CwNews;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * @author Alexander Dridiger
 */
@Root(name = "root", strict = false)
public class XmlBlogs extends XmlRoot {

    @ElementList(inline = true, required = false)
    private List<CwBlog> blogs;

    public List<CwBlog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<CwBlog> news) {
        this.blogs = blogs;
    }
}
