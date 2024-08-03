package com.blinder.visionvoice.gateway.output;

import lombok.Data;

import java.util.List;

@Data
public class ImageResponse {
    private CropHintsAnnotation cropHintsAnnotation;
    private ImagePropertiesAnnotation imagePropertiesAnnotation;
    private List<LabelAnnotation> labelAnnotations;
    private List<LocalizedObjectAnnotation> localizedObjectAnnotations;
}
