package com.blinder.visionvoice.gateway.output;

import lombok.Data;

import java.util.List;

@Data
public class ImagePropertiesAnnotation {
    private List<CropHint> cropHints;
    private DominantColors dominantColors;
}

