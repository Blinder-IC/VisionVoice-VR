package com.blinder.visionvoice.config;

import com.blinder.visionvoice.domain.repository.TtsRepository;
import org.springframework.stereotype.Service;

@Service
public class TextToSpeechService implements TtsRepository {

    @Override
    public byte[] synthesizeSpeech(String text) throws Exception {
        return new byte[0];
    }
}
