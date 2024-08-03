package com.blinder.visionvoice.gateway.output;

import lombok.Data;

@Data
public class ColorInfo {
    private Color color;
    private String hex;
    private double percent;
    private int percentRounded;
    private double pixelFraction;
    private String rgb;
    private double score;
}
