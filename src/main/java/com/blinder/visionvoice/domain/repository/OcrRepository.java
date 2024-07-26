package com.blinder.visionvoice.domain.repository;

import reactor.core.publisher.Mono;

public interface OcrRepository {
    Mono<String> extractText(byte[] imageBytes);
}
