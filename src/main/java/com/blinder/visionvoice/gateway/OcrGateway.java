package com.blinder.visionvoice.gateway;

import com.blinder.visionvoice.config.interceptor.FeignConfig;
import com.blinder.visionvoice.gateway.input.ImageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@FeignClient(name = "googleVisionClient", url = "https://vision.googleapis.com/v1/images:annotate", configuration = FeignConfig.class)
public interface OcrGateway {
    @PostMapping
    Mono<String> annotateImage(@RequestBody ImageRequest imageRequest);
}

