package com.blinder.visionvoice.gateway.input;

import lombok.Data;

import java.util.List;

@Data
public class ImageRequest {
    private List<Request> requests;

    @Data
    public static class Request {
        private Image image;
        private ImageContext imageContext;
        private List<Feature> features;
    }

    @Data
    public static class Image {
        private String content;
    }

    @Data
    public static class ImageContext {
        private CropHintsParams cropHintsParams;
    }

    @Data
    public static class CropHintsParams {
        private List<Double> aspectRatios;
    }

    @Data
    public static class Feature {
        private String type;
        private int maxResults;
        private String model;
    }
}
