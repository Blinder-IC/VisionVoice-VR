package com.blinder.visionvoice.gateway.output;

import lombok.Data;

@Data
public class LabelAnnotation {
    private String description;
    private String mid;
    private double score;
    private double topicality;
}
