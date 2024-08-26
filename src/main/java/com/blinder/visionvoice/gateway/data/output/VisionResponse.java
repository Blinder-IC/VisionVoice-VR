package com.blinder.visionvoice.gateway.data.output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisionResponse {
    @JsonProperty("responses")
    private List<Response> responses;

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        @JsonProperty("localizedObjectAnnotations")
        private List<LocalizedObjectAnnotation> localizedObjectAnnotations;

        public List<LocalizedObjectAnnotation> getLocalizedObjectAnnotations() {
            return localizedObjectAnnotations;
        }

        public void setLocalizedObjectAnnotations(List<LocalizedObjectAnnotation> localizedObjectAnnotations) {
            this.localizedObjectAnnotations = localizedObjectAnnotations;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LocalizedObjectAnnotation {
        @JsonProperty("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
