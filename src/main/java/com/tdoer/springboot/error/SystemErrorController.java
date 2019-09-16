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
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class SystemErrorController extends BasicErrorController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ErrorAttributes errorAttributes;

    public SystemErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties){
        super(errorAttributes, errorProperties);

        this.errorAttributes = errorAttributes;
    }

    @RequestMapping
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        reportError(request);

        return super.error(request);
    }

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData errorJson(HttpServletRequest request) {
        reportError(request);
        Map<String, Object> data = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);

        return ResponseData.status(status.value()).data(data);
    }

    @Override
    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        reportError(request);

        return super.errorHtml(request, response);
    }

    protected void reportError(HttpServletRequest request){
        if(logger.isTraceEnabled()) {
            Throwable cause = errorAttributes.getError(new ServletWebRequest(request));
            if(cause != null){
                logger.trace("Uncaught error found", cause);
            }
        }
    }
}
