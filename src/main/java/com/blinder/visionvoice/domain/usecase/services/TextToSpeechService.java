package com.blinder.visionvoice.domain.usecase.services;

import com.blinder.visionvoice.domain.repository.TtsRepository;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class TextToSpeechService implements TtsRepository {

    @Override
    public byte[] synthesizeSpeech(String text) throws Exception {
        return new byte[0];
    }

    @Override
    public byte[] convertTextToSpeech(String text, String outputPath) throws Exception {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("en-US")
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                    .build();

            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            ByteString audioContents = response.getAudioContent();

            try (OutputStream out = new FileOutputStream(outputPath)) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file \"" + outputPath + "\"");
                return audioContents.toByteArray();
            } catch (Exception e) {
                throw new Exception("Error writing audio file: " + e.getMessage());
            }
        }
    }
}
