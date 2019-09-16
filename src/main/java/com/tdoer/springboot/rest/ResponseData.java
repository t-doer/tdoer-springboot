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
public class ResponseData {
    protected int status;

    protected String message;

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Object data;

    public ResponseData() {

    }

    public ResponseData(int status, Object data){
        this(status, StatusCodeUtil.retrieveReasonPhrase(status), data);
    }

    public ResponseData(int status, String message, Object data){
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

    public Object getData() {
        return data;
    }

    public ResponseData data(Object data){
        this.data = data;
        return this;
    }

    public ResponseData message(String message){
        Assert.notNull(message, "message cannot be null");

        this.message = message;
        return this;
    }

    public ResponseData message(String messagePattern, Object ... messageVariables){
        Assert.notNull(messagePattern, "messagePattern cannot be null");
        Assert.notNull(messageVariables, "messageVariables cannot be null");

        message = MessageFormat.format(messagePattern, messageVariables);

        return this;
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

    public static ResponseData status(int status){
        return new ResponseData(status, null);
    }

    public static ResponseData status(int status, Object ... messageVariables){
        Assert.notNull(messageVariables, "messageVariables cannot be null");

        String phrase = StatusCodeUtil.retrieveReasonPhrase(status);
        phrase = MessageFormat.format(phrase, messageVariables);

        return new ResponseData(status, phrase, null);
    }

    // -----------------------------------------------------------
    // TOP 11 RESPONSE STATUS
    // -----------------------------------------------------------

    public static ResponseData ok(){
        return status(StatusCodes.OK);
    }

    public static ResponseData ok(Object data){
        return status(StatusCodes.OK).data(data);
    }

    public static ResponseData notFound(){
        return status(StatusCodes.NOT_FOUND);
    }

    public static ResponseData created(){
        return status(StatusCodes.CREATED);
    }

    public static ResponseData accepted(){
        return status(StatusCodes.ACCEPTED);
    }

    public static ResponseData noContent(){
        return status(StatusCodes.NO_CONTENT);
    }

    public static ResponseData badRequest(){
        return status(StatusCodes.BAD_REQUEST);
    }

    public static ResponseData unauthorized(){
        return status(StatusCodes.UNAUTHORIZED);
    }

    public static ResponseData forbidden(){
        return status(StatusCodes.FORBIDDEN);
    }

    public static ResponseData conflict(){
        return status(StatusCodes.CONFLICT);
    }

    public static ResponseData internalServerError(){
        return status(StatusCodes.INTERNAL_SERVER_ERROR);
    }

    public static ResponseData methodNotAllowed(){
        return status(StatusCodes.METHOD_NOT_ALLOWED);
    }

    public static ResponseData requestTimeout(){return status(StatusCodes.REQUEST_TIMEOUT);}

    @Override
    public String toString() {
        return "ResponseData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
