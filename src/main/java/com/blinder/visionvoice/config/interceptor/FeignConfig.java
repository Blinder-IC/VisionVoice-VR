package com.blinder.visionvoice.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    private static final String API_KEY = "AIzaSyAgxrAWYnBHYS-wAWvKz20-McgKABc4GCQ";

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.query("key", API_KEY);
            }
        };
    }
}
