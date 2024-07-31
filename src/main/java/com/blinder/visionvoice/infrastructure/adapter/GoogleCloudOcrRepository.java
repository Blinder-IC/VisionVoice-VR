package com.blinder.visionvoice.infrastructure.adapter;

import com.blinder.visionvoice.domain.repository.OcrRepository;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
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

                AnnotateImageResponse response = vision.batchAnnotateImages(requests).getResponsesList().get(0);
                if (response.hasError()) {
                    throw new IOException("Error: " + response.getError().getMessage());
                }

                return response.getTextAnnotationsList().get(0).getDescription();
            }
        });
    }
}
