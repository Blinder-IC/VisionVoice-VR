package com.blinder.visionvoice.domain.usecase;

import com.blinder.visionvoice.application.services.VisionService;
import com.blinder.visionvoice.domain.repository.TtsRepository;
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

    public Mono<byte[]> processImage(byte[] imageBytes) {
        return visionService.sendImageRequest(imageBytes)
                .flatMap(detectedText -> {
                    try {
                        String outputPath = "output.mp3";
                        return Mono.just(ttsRepository.convertTextToSpeech(detectedText, outputPath ));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }
}
