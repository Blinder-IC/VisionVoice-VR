package com.blinder.visionvoice.config;

import com.blinder.visionvoice.domain.repository.OcrRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OcrService implements OcrRepository {

    @Value("${ocr.space.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public OcrService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.ocr.space/parse/image")
                .build();
    }

    @Override
    public Mono<String> extractText(byte[] imageBytes) {
        return webClient.post()
                .uri("?apikey=" + apiKey)
                .bodyValue(imageBytes)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    // Process response to extract text
                    // You might need to parse the JSON response
                    return response; // Simplified for this example
                });
    }
}
