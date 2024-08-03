package com.blinder.visionvoice.application.services;

import com.blinder.visionvoice.gateway.OcrGateway;
import com.blinder.visionvoice.gateway.input.ImageRequest;
import com.blinder.visionvoice.gateway.output.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class VisionService {

    private final OcrGateway ocrGateway;

    public VisionService(OcrGateway ocrGateway) {
        this.ocrGateway = ocrGateway;
    }

    public Mono<String> sendImageRequest(byte[] imageBytes) {
        ImageRequest imageRequest = new ImageRequest();
        ImageRequest.Request request = new ImageRequest.Request();

        // Convert imageBytes to base64 string
        String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

        // Set Image content
        ImageRequest.Image image = new ImageRequest.Image();
        image.setContent(base64Image);
        request.setImage(image);

        // Set CropHintsParams
        ImageRequest.CropHintsParams cropHintsParams = new ImageRequest.CropHintsParams();
        cropHintsParams.setAspectRatios(Arrays.asList(0.8, 1.0, 1.2));

        ImageRequest.ImageContext imageContext = new ImageRequest.ImageContext();
        imageContext.setCropHintsParams(cropHintsParams);
        request.setImageContext(imageContext);

        // Set Features
        request.setFeatures(Arrays.asList(
                createFeature("LANDMARK_DETECTION", 50, null),
                createFeature("FACE_DETECTION", 50, null),
                createFeature("OBJECT_LOCALIZATION", 50, "builtin/latest"),
                createFeature("LOGO_DETECTION", 50, "builtin/latest"),
                createFeature("LABEL_DETECTION", 50, null),
                createFeature("DOCUMENT_TEXT_DETECTION", 50, "builtin/latest"),
                createFeature("SAFE_SEARCH_DETECTION", 50, null),
                createFeature("IMAGE_PROPERTIES", 50, null),
                createFeature("CROP_HINTS", 50, null)
        ));

        imageRequest.setRequests(Arrays.asList(request));

        System.out.println(imageRequest);

        ResponseEntity<ImageResponse> ggcvReponse = ocrGateway.annotateImage(imageRequest);


        return null;
    }

    private ImageRequest.Feature createFeature(String type, int maxResults, String model) {
        ImageRequest.Feature feature = new ImageRequest.Feature();
        feature.setType(type);
        feature.setMaxResults(maxResults);
        feature.setModel(model);
        return feature;
    }
}