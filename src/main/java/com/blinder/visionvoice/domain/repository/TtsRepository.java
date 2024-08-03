package com.blinder.visionvoice.domain.repository;

public interface TtsRepository {
    byte[] synthesizeSpeech(String text) throws Exception;

    byte[] convertTextToSpeech(String text, String outputPath) throws Exception;
}
