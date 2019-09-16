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

import com.tdoer.springboot.error.ControllerErrorHandler;
import com.tdoer.springboot.error.ResponseDataAdvice;
import com.tdoer.springboot.error.SystemErrorController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;
/**
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@EnableConfigurationProperties({ ServerProperties.class, ResourceProperties.class })
public class ErrorHandlerConfiguration {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private final ServerProperties serverProperties;
    private final ResourceProperties resourceProperties;

    public ErrorHandlerConfiguration(ApplicationContext applicationContext, ServerProperties serverProperties, ResourceProperties resourceProperties) {
        this.applicationContext = applicationContext;
        this.serverProperties = serverProperties;
        this.resourceProperties = resourceProperties;
    }

    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes(
                this.serverProperties.getError().isIncludeException());
    }

    @Bean
    public SystemErrorController basicErrorController(ErrorAttributes errorAttributes) {
        return new SystemErrorController(errorAttributes, this.serverProperties.getError());
    }

    @Bean
    public ControllerErrorHandler controllerErrorHandler(){
        return new ControllerErrorHandler(this.serverProperties.getError());
    }

    @Bean
    public ResponseDataAdvice responseBodyAdvice(){
        return new ResponseDataAdvice();
    }
}
