/*
 * Copyright 2017-2019 T-Doer (tdoer.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tdoer.springboot.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageSerializable;

import java.util.Collection;
import java.util.List;

/**
 * 分页返回对象
 *
 * @author conan
 * @create 2019-09-09
 */
public class PageResult<T> extends PageSerializable<T> {
    private int pageNum;
    private int pageSize;

    public PageResult() {

    }

    public PageResult(List<T> list) {
        this(list, 8);
    }

    public PageResult(List<T> list, int navigatePages) {
        super(list);
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
        }
    }

    public static <T> PageResult<T> of(List<T> list) {
        return new PageResult(list);
    }

    public static <T> PageResult<T> of(List<T> list, int navigatePages) {
        return new PageResult(list, navigatePages);
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PageResult{");
        sb.append("pageNum=").append(this.pageNum);
        sb.append(", pageSize=").append(this.pageSize);
        sb.append(", total=").append(this.total);
        sb.append(", list=").append(this.list);
        sb.append('}');
        return sb.toString();
    }
}
