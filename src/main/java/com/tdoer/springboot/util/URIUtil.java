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
package com.tdoer.springboot.util;

import com.tdoer.springboot.error.ErrorCodeException;
import com.tdoer.springboot.http.StatusCodes;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class URIUtil {

    public static URI getURI(String domain, String path) throws ErrorCodeException {
        try{
            if(!domain.endsWith("/")){
                domain = domain + "/";
            }
            if(StringUtils.hasText(path)){
                if(path.startsWith("/")){
                    path = path.substring(1);
                }
                domain = domain + path;
            }
            return new URI(domain);
        }catch (URISyntaxException use){
            throw new ErrorCodeException(StatusCodes.INVALID_URI, domain);
        }
    }

}
