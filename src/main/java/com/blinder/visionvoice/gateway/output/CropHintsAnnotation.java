package com.blinder.visionvoice.gateway.output;

import lombok.Data;

import java.util.List;

@Data
public class CropHintsAnnotation {
    private List<CropHint> cropHints;
}

