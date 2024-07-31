package com.blinder.visionvoice.domain.usecase;

import com.blinder.visionvoice.config.TextToSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertTextToSpeechUseCase {

    private final TextToSpeechService textToSpeechService;

    @Autowired
    public ConvertTextToSpeechUseCase(TextToSpeechService textToSpeechService) {
        this.textToSpeechService = textToSpeechService;
    }

    public void execute(String text, String outputPath) throws Exception {
        textToSpeechService.convertTextToSpeech(text, outputPath);
    }
}