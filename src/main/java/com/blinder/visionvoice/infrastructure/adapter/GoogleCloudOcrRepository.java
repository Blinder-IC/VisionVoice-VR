package com.blinder.visionvoice.infrastructure.adapter;

import com.blinder.visionvoice.domain.repository.OcrRepository;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleCloudOcrRepository implements OcrRepository {

    @Override
    public Mono<String> extractText(byte[] imageBytes) {
        return Mono.fromCallable(() -> {
            try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
                ByteString imgBytes = ByteString.copyFrom(imageBytes);

                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .addFeatures(feat)
                        .setImage(img)
                        .build();
                List<AnnotateImageRequest> requests = Collections.singletonList(request);

                AnnotateImageResponse response = vision.batchAnnotateImages(requests).getResponsesList().getFirst();
                if (response.hasError()) {
                    throw new IOException("Error: " + response.getError().getMessage());
                }

                List<EntityAnnotation> textAnnotations = response.getTextAnnotationsList();
                if (!textAnnotations.isEmpty()) {
                    return textAnnotations.getFirst().getDescription();
                } else {
                    throw new IOException("No text annotations found in the image.");
                }
            }
        });
    }

}