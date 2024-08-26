package com.blinder.visionvoice.domain.usecase;

import com.blinder.visionvoice.domain.usecase.services.VisionService;
import com.blinder.visionvoice.domain.repository.TtsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class ProcessImageUseCase {

    @Autowired
    VisionService visionService;

    @Autowired
    private TtsRepository ttsRepository;

    public Mono<byte[]> processImage(byte[] imageBytes) throws JsonProcessingException {
        return visionService.sendImageRequest(imageBytes)
                .flatMap(detectedText -> {
                    try {
                        String outputPath = "output.mp3";
                        return Mono.just(ttsRepository.convertTextToSpeech((String) detectedText, outputPath ));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}
