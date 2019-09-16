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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class WebUtil {
    protected static Logger logger = LoggerFactory.getLogger(WebUtil.class);

    /**
     * Find request value with the priority, from highest to lowest:
     * <ol>
     *     <li>Request Parameter</li>
     *     <li>Request Header</li>
     *     <li>Request Cookie</li>
     *     <li>Request Attribute</li>
     * </ol>
     * @param request
     * @param name
     * @return
     */
    public static String findValueFromRequest(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null) {
            logger.debug("The value of '{}' not found in request parameters, trying next", name);

            value = request.getHeader(name);
            if (value == null) {
                logger.debug("The value of '{}' not found in request header, trying next", name);

                value = CookieUtil.getCookieValue(request, name);
                if (value == null) {
                    logger.debug("The value of '{}' not found in cookie, trying next", name);
                    if (request.getAttribute(name) != null) {
                        value = request.getAttribute(name).toString();
                        if (value == null) {
                            logger.debug("The value of '{}' not found in request attributes, trying next", name);
                        }
                    }
                }
            }
        }

        logger.info("The value of '{}' in request is finally: {}", name, value);

        return value;
    }

    public static void addValueIntoResponseHeaderAndCookie(HttpServletResponse response, HttpServletRequest request, String name, String value) {

        // The request is from an APP
        // Add header for APP
        response.addHeader(name, value);

        // From others
        // Add cookie for Browser
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        CookieUtil.addCookie(response, cookie);

        logger.info("Add value '{}' into response header and cookie '{}", value, name);
    }

    public static void removeValueFromCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        CookieUtil.addCookie(response, cookie);
    }

    public static String getRequestParameterFromReferer(HttpServletRequest request, String parameterName) {
        String ret = null;
        String referer = request.getHeader("referer");
        if (referer != null) {
            UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(referer).build(false);
            ret = uriComponents.getQueryParams().getFirst(parameterName);
        }

        return ret;
    }

}
