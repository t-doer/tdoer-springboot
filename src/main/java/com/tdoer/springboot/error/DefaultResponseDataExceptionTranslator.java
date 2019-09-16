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
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class DefaultResponseDataExceptionTranslator implements ResponseDataExceptionTranslator{
    @Override
    public ResponseData translate(Throwable e) {
        if(e instanceof ErrorCodeException){
            ErrorCodeException ece = (ErrorCodeException)e;
            return new ResponseData(ece.getErrorCode(), ece.getErrorMessage());
        }

        return ResponseData.internalServerError();
    }
    
}