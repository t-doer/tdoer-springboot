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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class CookieUtil {

    public static String getCookieValue(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static void addCookie(HttpServletResponse response, Cookie cookie){
        response.addCookie(cookie);
    }

    public static void addDefaultCookie(HttpServletResponse response, String cookieName, String cookieValue){
        Cookie cookie = new Cookie(cookieName, cookieValue);
        addCookie(response, cookie);
    }

    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge, String domain,
                                 String path, boolean secure, boolean httpOnly, String comment){
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly);
        cookie.setComment(comment);
        addCookie(response, cookie);
    }

}
