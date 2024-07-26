package com.blinder.visionvoice.application.services;

import com.blinder.visionvoice.domain.usecase.ProcessImageUseCase;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class OcrTtsService {

    @Autowired
    private ProcessImageUseCase processImageUseCase;

    public Mono<byte[]> processImage(byte[] imageBytes) {
        return processImageUseCase.processImage(imageBytes);
    }
}
