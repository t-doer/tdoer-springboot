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
package com.tdoer.springboot.autoconfigure;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @description Jackson2CustomerAutoConfig
 * @author fly_once(654126198@qq.com)
 * @create 2019-11-21
 */
@Configuration
@ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class Jackson2CustomerAutoConfig {

    private static Logger logger = LoggerFactory.getLogger(Jackson2CustomerAutoConfig.class);

    @Autowired
    private Jackson2ObjectMapperBuilder builder;

    @Bean
    public CustomerJackson2DateFormat customizerDateFormat() {
        CustomerJackson2DateFormat customerFormat = new CustomerJackson2DateFormat();
        this.builder.dateFormat(customerFormat);
        return customerFormat;
    }

    class CustomerJackson2DateFormat extends DateFormat {
        private SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        private SimpleDateFormat formater_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private SimpleDateFormat formater_YYYY_MM_DD_HH_MM_SS_SSSZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        public CustomerJackson2DateFormat() {
            formater.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }

        @Override
        public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
            return formater.format(date, toAppendTo, fieldPosition);
        }

        @Override
        public Date parse(String source, ParsePosition pos) {
            Date result = fromYYYYMMDD_HH_MM_SS(source, pos);
            if (result != null)
                return result;
            result = fromYYYY_MM_DD_HH_MM_SS(source, pos);
            if (result != null)
                return result;
            result = fromUnixTimestamp(source, pos);
            if (result != null) {
                return result;
            } else {
                logger.error(String.format("Cannot parse %s to java.util.Date, time should represent in [%s]", source,
                        "'yyyy-MM-dd HH:mm:ss'/'yyyy/MM/dd HH:mm:ss'/UnixTimestamp"));
            }
            return null;
        }

        private Date fromUnixTimestamp(String timestamp, ParsePosition pos) {
            try {
                Date parsedDate = new Date(Long.valueOf(timestamp));
                // modify pos index to avoid throw Unparserable exception
                pos.setIndex(timestamp.length() - 1);
                return parsedDate;
            } catch (Exception e) {
                return null;
            }
        }

        @SuppressWarnings("unused")
        private String toUnixTimestamp(Date date) {
            return Long.valueOf(date.getTime()).toString();
        }

        private Date fromYYYY_MM_DD_HH_MM_SS(String source, ParsePosition pos) {
            try {
                return formater_YYYY_MM_DD_HH_MM_SS.parse(source, pos);
            } catch (Exception e) {
                return null;
            }
        }

        private Date fromYYYYMMDD_HH_MM_SS(String source, ParsePosition pos) {
            try {
                return formater.parse(source, pos);
            } catch (Exception e) {
                return null;
            }
        }

        @SuppressWarnings("unused")
        private Date fromYYYY_MM_DD_T_HH_MM_SS_SSSZ(String source, ParsePosition pos) {
            return formater_YYYY_MM_DD_HH_MM_SS_SSSZ.parse(source, pos);
        }

        @Override
        public DateFormat clone() {
            return new CustomerJackson2DateFormat();
        }
    }
}