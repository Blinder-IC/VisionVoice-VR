package com.blinder.visionvoice.domain.usecase.services;

import com.blinder.visionvoice.domain.usecase.ProcessImageUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class OcrTtsService {

    @Autowired
    private ProcessImageUseCase processImageUseCase;

    public Mono<byte[]> processImage(byte[] imageBytes) throws JsonProcessingException {
        return processImageUseCase.processImage(imageBytes);
    }
}
