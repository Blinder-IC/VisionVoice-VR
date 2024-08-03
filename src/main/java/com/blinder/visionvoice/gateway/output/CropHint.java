package com.blinder.visionvoice.gateway.output;

import lombok.Data;

@Data
public class CropHint {
    private BoundingPoly boundingPoly;
    private double confidence;
    private double importanceFraction;
}
