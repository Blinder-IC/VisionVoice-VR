package com.blinder.visionvoice.gateway.output;

import lombok.Data;

import java.util.List;

@Data
public class BoundingPoly {
    private List<Vertex> vertices;
    private List<NormalizedVertex> normalizedVertices;
}

