package com.blinder.visionvoice.application.controller;

import com.blinder.visionvoice.domain.usecase.services.OcrTtsService;
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
@RequestMapping("/blinder")
public class OcrTtsController {
    private final OcrTtsService ocrTtsService;

    @Autowired
    public OcrTtsController(OcrTtsService ocrTtsService) {
        this.ocrTtsService = ocrTtsService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processImage(@RequestParam("file") MultipartFile file) {
        try {
            byte[] imageBytes = file.getBytes();
            ocrTtsService.processImage(imageBytes).block();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
