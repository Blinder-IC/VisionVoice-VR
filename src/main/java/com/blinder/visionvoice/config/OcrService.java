package com.blinder.visionvoice.application.services;

import com.blinder.visionvoice.domain.repository.OcrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OcrService {

    private final OcrRepository ocrRepository;

    @Autowired
    public OcrService(OcrRepository ocrRepository) {
        this.ocrRepository = ocrRepository;
    }

    public Mono<String> processImage(byte[] imageBytes) {
        return ocrRepository.extractText(imageBytes);
    }
}
