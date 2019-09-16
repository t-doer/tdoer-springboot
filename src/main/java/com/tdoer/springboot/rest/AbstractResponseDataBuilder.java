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
package com.tdoer.springboot.rest;

import com.tdoer.springboot.http.StatusCodes;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public abstract class AbstractResponseDataBuilder<O, T extends GenericResponseData<O>> {

    protected int status = StatusCodes.OK; // default status is OK

    protected String message;

    protected O data;

    public abstract T build();

    public AbstractResponseDataBuilder<O, T> data(O data) {
        this.data = data;
        return this;
    }

    public AbstractResponseDataBuilder<O, T> message(String message) {
        this.message = message;
        return this;
    }

    public AbstractResponseDataBuilder<O, T> status(int status) {
        this.status = status;
        return this;
    }

    // -----------------------------------------------------------
    // TOP 11 RESPONSE STATUS
    // -----------------------------------------------------------

    public AbstractResponseDataBuilder<O, T> ok() {
        return status(StatusCodes.OK);
    }

    public AbstractResponseDataBuilder<O, T> ok(O data) {
        return status(StatusCodes.OK).data(data);
    }

    public AbstractResponseDataBuilder<O, T> notFound() {
        return status(StatusCodes.NOT_FOUND);
    }

    public AbstractResponseDataBuilder<O, T> created() {
        return status(StatusCodes.CREATED);
    }

    public AbstractResponseDataBuilder<O, T> accepted() {
        return status(StatusCodes.ACCEPTED);
    }

    public AbstractResponseDataBuilder<O, T> noContent() {
        return status(StatusCodes.NO_CONTENT);
    }

    public AbstractResponseDataBuilder<O, T> badRequest() {
        return status(StatusCodes.BAD_REQUEST);
    }

    public AbstractResponseDataBuilder<O, T> unauthorized() {
        return status(StatusCodes.UNAUTHORIZED);
    }

    public AbstractResponseDataBuilder<O, T> forbidden() {
        return status(StatusCodes.FORBIDDEN);
    }

    public AbstractResponseDataBuilder<O, T> conflict() {
        return status(StatusCodes.CONFLICT);
    }

    public AbstractResponseDataBuilder<O, T> internalServerError() {
        return status(StatusCodes.INTERNAL_SERVER_ERROR);
    }

    public AbstractResponseDataBuilder<O, T> methodNotAllowed() {
        return status(StatusCodes.METHOD_NOT_ALLOWED);
    }

    public AbstractResponseDataBuilder<O, T> requestTimeout() {
        return status(StatusCodes.REQUEST_TIMEOUT);
    }
}
