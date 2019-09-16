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
package com.tdoer.springboot.error;

import com.tdoer.springboot.util.StatusCodeUtil;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class ErrorCodeException extends RuntimeException {

    protected int errorCode;

    protected String errorMessage;

    public ErrorCodeException(int errorCode) {

        this(errorCode, null, null);
    }

    public ErrorCodeException(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorCodeException(int errorCode, Throwable cause) {
        this(errorCode, cause, null);
    }

    public ErrorCodeException(int errorCode, Object... messageFormatArgs){
        this(errorCode, null, messageFormatArgs);
    }

    public ErrorCodeException(int errorCode, Throwable cause, Object... messageFormatArgs) {
        super(cause);

        String message = StatusCodeUtil.retrieveReasonPhrase(errorCode);
        if(message == null){
            throw new IllegalArgumentException("Error code not defined properly yet: " + errorCode);
        }

        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("Error message not defined properly yet for the error code: " + errorCode);
        }

        if(messageFormatArgs != null){
            message = MessageFormat.format(message, messageFormatArgs);
        }

        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getMessage() {
        return getErrorMessage();
    }
}
