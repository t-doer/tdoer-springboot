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

import com.tdoer.springboot.annotation.ReasonPhrase;
import com.tdoer.springboot.http.StatusCodes;
import com.tdoer.springboot.util.StatusCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.Field;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public class StatusCodesRegistrar implements ApplicationListener<ApplicationStartedEvent> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public StatusCodesRegistrar(){
        logger.info("StatusCodesRegistrar is initialized");
    }

    /**
     * Find out {@link StatusCodes} from {@link EnableErrorHandler} annotation and register them.
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        logger.debug("Application Event is fired: {}", event);

        Class<?> mainClass = event.getSpringApplication().getMainApplicationClass();
        if(mainClass != null){
            logger.debug("Main application class is: {}", mainClass.getName());
            EnableErrorHandler annotation = mainClass.getDeclaredAnnotation(EnableErrorHandler.class);
            if(annotation != null){
                logger.debug("Found annotation: {}", EnableErrorHandler.class);
                Class<? extends StatusCodes>[] errorCodes = annotation.value();
                if(errorCodes != null){
                    for(Class<? extends StatusCodes> errorCode : errorCodes){
                        logger.debug("Found StatusCodes class: {}", errorCode.getName());
                        StatusCodeUtil.registerStatusCodes(errorCode);
                    }
                }else{
                    logger.debug("No any StatusCodes class found.");
                }
            }

        }
    }

}
