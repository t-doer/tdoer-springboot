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
import com.tdoer.springboot.util.StatusCodeUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */

@ControllerAdvice
public class ResponseDataAdvice implements ResponseBodyAdvice<ResponseData> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if(ResponseData.class.isAssignableFrom(methodParameter.getParameterType())){
            return true;
        }
        return false;
    }

    @Override
    public ResponseData beforeBodyWrite(ResponseData responseData, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        int status = StatusCodeUtil.tuneStatusCode(responseData.getStatus());
        serverHttpResponse.setStatusCode(HttpStatus.valueOf(status));
        return responseData;
    }
}
