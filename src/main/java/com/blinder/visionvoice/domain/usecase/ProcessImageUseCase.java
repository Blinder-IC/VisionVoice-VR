package com.blinder.visionvoice.domain.usecase;

import com.blinder.visionvoice.domain.repository.OcrRepository;
import com.blinder.visionvoice.domain.repository.TtsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class ProcessImageUseCase {

    private OcrRepository ocrRepository;

    @Autowired
    private TtsRepository ttsRepository;

    public Mono<byte[]> processImage(byte[] imageBytes) {
        return ocrRepository.extractText(imageBytes)
                .flatMap(detectedText -> {
                    try {
                        return Mono.just(ttsRepository.synthesizeSpeech(detectedText));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}
