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

import com.tdoer.springboot.rest.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */

@ControllerAdvice
@RestController
public class ControllerErrorHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ResponseDataExceptionTranslator exceptionTranslator = new DefaultResponseDataExceptionTranslator();

    protected final ErrorProperties errorProperties;

    public ControllerErrorHandler(ErrorProperties errorProperties) {
        this.errorProperties = errorProperties;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseData processBusinessException(ErrorCodeException businessException, HttpServletRequest httpServletRequest) {
        logger.error("Exception found when processing request {}", httpServletRequest.getRequestURL());
        logger.error(businessException.getErrorMessage(), businessException);
        Throwable cause = null;
        if (errorProperties.isIncludeException()) {
            cause = businessException.getCause();
        }

        return ResponseData.status(businessException.getErrorCode()).message(businessException.getErrorMessage()).data(cause);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseData processThrowable(Throwable throwable, HttpServletRequest httpServletRequest) {
        logger.error("Exception found when processing request {}", httpServletRequest.getRequestURL());
        logger.error("Uncaught exception found", throwable);
        return exceptionTranslator.translate(throwable);
    }
}
