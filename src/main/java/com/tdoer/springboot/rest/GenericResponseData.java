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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tdoer.springboot.http.StatusCodes;
import com.tdoer.springboot.util.StatusCodeUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.text.MessageFormat;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class GenericResponseData<T> {
    protected int status;

    protected String message;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected T data;

    private GenericResponseData() {// for persistence

    }

    public GenericResponseData(T data){
        this(StatusCodes.OK, data);
    }

    public GenericResponseData(int status){
        this(status, null);
    }

    public GenericResponseData(int status, T data){
        this(status, StatusCodeUtil.retrieveReasonPhrase(status), data);
    }

    public GenericResponseData(int status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(int status, Object ... messageVariables) {

        Assert.notNull(messageVariables, "messageVariables cannot be null");

        String phrase = StatusCodeUtil.retrieveReasonPhrase(status);
        phrase = MessageFormat.format(phrase, messageVariables);

        this.status = status;
        this.message = phrase;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message, Object ... messageVariables) {

        Assert.notNull(message, "message cannot be null");
        Assert.notNull(messageVariables, "messageVariables cannot be null");

        this.message = MessageFormat.format(message, messageVariables);
    }

    public void setData(@Nullable T data) {
        this.data = data;
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isOK(){
        if(status >= 200 && status < 300){
            return true;
        }else{
            return false;
        }
    }

    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean notOK(){
        return !isOK();
    }


    public static GenericResponseData ok() {
        return new GenericResponseData<>(StatusCodes.OK, null, null);
    }

    public static <T> GenericResponseData<T> ok(T data) {
        return new GenericResponseData<>(StatusCodes.OK, null, data);
    }


    @Override
    public String toString() {
        return "GenericResponseData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
