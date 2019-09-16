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

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class NetworkUtil {

	/**
	 * Get a request's remote address which may be going througn proxies.
	 *
	 * @param request The request
	 * @return IP address or local host address
	 */
	public final static String getRemoteAddr(HttpServletRequest request) {
		String address = request.getHeader("X-Forwarded-For");
		boolean notFound = notFound(address);

		if(notFound){
			address = request.getHeader("Proxy-Client-IP");
			notFound = notFound(address);
		}
		if(notFound){
			address = request.getHeader("WL-Proxy-Client-IP");
			notFound = notFound(address);
		}
		if(notFound){
			address = request.getHeader("HTTP_CLIENT_IP");
			notFound = notFound(address);
		}
		if(notFound){
			address = request.getHeader("HTTP_X_FORWARDED_FOR");
			notFound = notFound(address);
		}
		if(notFound){
			address = request.getRemoteAddr();
		}

		if(address.indexOf(",") != -1){
			String[] ips = address.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!notFound(strIp)) {
					address = strIp;
					break;
				}
			}
		}

		return address;
	}

	private static boolean notFound(String address){
		if(!StringUtils.hasText(address)){
			return true;
		}

		if("unknown".equalsIgnoreCase(address)){
			return true;
		}

		return false;
	}

}
