package com.blinder.visionvoice.gateway.output;

import lombok.Data;

@Data
public class LocalizedObjectAnnotation {
    private BoundingPoly boundingPoly;
    private String mid;
    private String name;
    private double score;
}
