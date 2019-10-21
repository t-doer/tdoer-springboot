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

import com.tdoer.springboot.annotation.ReasonPhrase;
import com.tdoer.springboot.http.StatusCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class StatusCodeUtil {
    private static Logger logger = LoggerFactory.getLogger(StatusCodeUtil.class);

    private static final String PREFIX = "__STATUS_CODE_";

    public static void registerStatusCodes(Class<? extends StatusCodes> statusCodes){
        for(Field field: statusCodes.getFields()){
            ReasonPhrase reasonPhrase = field.getAnnotation(ReasonPhrase.class);
            if(reasonPhrase != null){
                try{
                    registryStatusCode(field.getInt(null), reasonPhrase.value());
                }catch (IllegalAccessException iae){
                    logger.warn("Failed to get value of the field: {}", field.getName());
                }
            }else{
                logger.warn("No ReasonPhrase annoation declared for the field: {}", field.getName());
            }

        }
    }

    public static void registryStatusCode(int statusCode, String reasonPhrase){
        System.setProperty(key(statusCode), reasonPhrase);
    }

    public static String retrieveReasonPhrase(int statusCode){
        return System.getProperty(key(statusCode));
    }

    private static String key(int statusCode){
        return PREFIX + statusCode;
    }

    /**
     * Whether this status code is in the HTTP "INFORMATIONAL" series
     */
    public static boolean is1xxInformational(int statusCode) {
        return Series.INFORMATIONAL.equals(Series.valueOf(statusCode));
    }

    /**
     * Whether this status code is in the HTTP "SUCCESSFUL" series
     */
    public static boolean is2xxSuccessful(int statusCode) {
        return Series.SUCCESSFUL.equals(Series.valueOf(statusCode));
    }

    /**
     * Whether this status code is in the HTTP "REDIRECTION" series
     */
    public static boolean is3xxRedirection(int statusCode) {
        return Series.REDIRECTION.equals(Series.valueOf(statusCode));
    }


    /**
     * Whether this status code is in the HTTP "CLIENT_ERROR" series
     */
    public static boolean is4xxClientError(int statusCode) {
        return Series.CLIENT_ERROR.equals(Series.valueOf(statusCode));
    }

    /**
     * Whether this status code is in the HTTP "SERVER_ERROR" series
     */
    public static boolean is5xxServerError(int statusCode) {
        return Series.SERVER_ERROR.equals(Series.valueOf(statusCode));
    }

    /**
     * Whether this status code is in the HTTP "CLIENT_ERROR" or "SERVER_ERROR" series
     */
    public static boolean isError(int statusCode) {
        return is4xxClientError(statusCode) || is5xxServerError(statusCode);
    }

    /**
     * Custom status code (eg. 40001, 50001 etc.) will to transformed to be 200
     * @param statusCode
     * @return
     */
    public static int tuneStatusCode(int statusCode){
        int ret = statusCode;
        if(statusCode > 599){
            logger.debug("Status code {} is tuned to be {}", statusCode, StatusCodes.OK);
            return StatusCodes.OK;
        }
      return ret;
    }

    public static HttpStatus valueOf(int statusCode){
        return HttpStatus.valueOf(tuneStatusCode(statusCode));
    }

    /**
     * Enumeration of HTTP status series.
     * <p>Retrievable via {@link HttpStatus#series()}.
     */
    protected enum Series {

        INFORMATIONAL(1),
        SUCCESSFUL(2),
        REDIRECTION(3),
        CLIENT_ERROR(4),
        SERVER_ERROR(5);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        /**
         * Return the integer value of this status series. Ranges from 1 to 5.
         */
        public int value() {
            return this.value;
        }

        /**
         * Decide series for the status code.
         */
        public static Series valueOf(int status) {
            int seriesCode = -1;
            if(status >= 100 && status < 600){
                seriesCode = status / 100;
            }else {
                seriesCode = 5; // server error
            }

            for (Series series : values()) {
                if (series.value == seriesCode) {
                    return series;
                }
            }

            throw new IllegalArgumentException("No matching constant for [" + status + "]");
        }

    }
}
