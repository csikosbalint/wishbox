/*
 *   WishboxCrawler.java is part of the "wishbox ( crawler )" project
 *   Copyright (C)  2015  author:  johnnym
 *
 *   This program is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU General Public License
 *   as published by the Free Software Foundation; either version 2
 *   of the License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package hu.fnf.devel.wishbox.crawler;

import com.fasterxml.classmate.TypeResolver;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class WishboxCrawler {
    public static final String ROOT = "/crawler";
    private static final Logger logger = LoggerFactory.getLogger(WishboxCrawler.class);
    @Autowired
    private static ApplicationContext appContext;
    @Autowired
    private TypeResolver typeResolver;

    public static void main(String[] args) {
        logger.info("Starting..." + WishboxCrawler.class.getCanonicalName());
        SpringApplication.run(WishboxCrawler.class, args);

    }

    @Bean
    public Docket getApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(input -> !input.contains("error"))
                .build().pathMapping(WishboxCrawler.ROOT)
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(newRule(typeResolver
                                .resolve(DeferredResult.class, typeResolver
                                                .resolve(ResponseEntity.class, WildcardType.class
                                                )
                                ), typeResolver.resolve(WildcardType.class)
                ))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(
                        RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("500 message")
                                        .responseModel(new ModelRef("Error"))
                                        .build()
                        )
                );
    }
}
