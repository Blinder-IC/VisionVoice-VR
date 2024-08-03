package com.blinder.visionvoice.application.controller;

import com.blinder.visionvoice.application.services.OcrTtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/test")
public class OcrTtsTestController {

    private final OcrTtsService ocrTtsService;

    @Autowired
    public OcrTtsTestController(OcrTtsService ocrTtsService) {
        this.ocrTtsService = ocrTtsService;
    }

    @PostMapping("/ocr-tts")
    public ResponseEntity<byte[]> processImage(@RequestParam("file") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            byte[] audioBytes = ocrTtsService.processImage(imageBytes).block();
            return new ResponseEntity<>(audioBytes, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
