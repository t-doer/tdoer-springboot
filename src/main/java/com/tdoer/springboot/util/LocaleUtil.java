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

import org.springframework.util.Assert;

import java.util.Locale;

/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class LocaleUtil {
    /**
     * Get locale from specific locale string. The format of locale string must be
     * '[language]_[country]', for example, en_US, zh_CN, es_ES, zh_TW etc.. If
     * the locale string is null, system default locale will be returned.
     */
    public static Locale getLocale(String locale) {
        Assert.hasText(locale, "Locale cannot be blank");

        if (locale.length() == 5 && locale.indexOf("_") == 2) {
            return (new Locale(locale.substring(0, 2), locale.substring(3, 5)));
        } else {
            throw new IllegalArgumentException("Unsupported locale format: " + locale);
        }
    }

    /**
     * Get locale string from specific locale. The format of locale string must be
     * '[language]_[country]', for example, en_US, zh_CN, es_ES, zh_TW etc.. If
     * the locale is null, system default locale will be used.
     */
    public static String getLocale(Locale locale) {
        Assert.notNull(locale, "Locale cannot be null");

        return (locale.getLanguage() + "_" + locale.getCountry());
    }
}
