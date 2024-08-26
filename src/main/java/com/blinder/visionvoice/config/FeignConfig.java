package com.blinder.visionvoice.config;

import com.blinder.visionvoice.config.interceptor.FeignInterceptorError;
import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignInterceptorError();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Logger feignLogger() {
        return new Slf4jLogger();
    }
}
