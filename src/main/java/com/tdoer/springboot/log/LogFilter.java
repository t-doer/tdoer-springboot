/*
 *
 *  Copyright 2017-2019 T-Doer (tdoer.com).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.tdoer.springboot.log;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * @author conan (kly824968443@gmail.com)
 * @create 2019/9/18
 * @description
 */
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String logId = "logId";
        String uuid = UUID.randomUUID().toString();
        MDC.put(logId, uuid);

        String logLevel = "logLevel";
        String value = servletRequest.getParameter(logLevel);
        if (!StringUtils.isEmpty(value)) {
            MDC.put(logLevel, value.toUpperCase());
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(logId);
            MDC.remove(logLevel);
        }
    }
}
