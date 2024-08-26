package com.blinder.visionvoice.domain.usecase.services;

import com.blinder.visionvoice.gateway.data.output.VisionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisionService {
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper objectMapper = new ObjectMapper();

    public Mono<Object> sendImageRequest(byte[] imageBytes) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        String requestBody = String.format("""
                {
                    "requests": [
                        {
                            "features": [
                                {"maxResults": 50, "type": "LANDMARK_DETECTION"},
                                {"maxResults": 50, "type": "FACE_DETECTION"},
                                {"maxResults": 50, "model": "builtin/latest", "type": "OBJECT_LOCALIZATION"},
                                {"maxResults": 50, "model": "builtin/latest", "type": "LOGO_DETECTION"},
                                {"maxResults": 50, "type": "LABEL_DETECTION"},
                                {"maxResults": 50, "model": "builtin/latest", "type": "DOCUMENT_TEXT_DETECTION"},
                                {"maxResults": 50, "type": "SAFE_SEARCH_DETECTION"},
                                {"maxResults": 50, "type": "IMAGE_PROPERTIES"},
                                {"maxResults": 50, "type": "CROP_HINTS"}
                            ],
                            "image": {"content": "%s"},
                            "imageContext": {
                                "cropHintsParams": {
                                    "aspectRatios": [0.8, 1, 1.2]
                                }
                            }
                        }
                    ]
                }
                """, base64Image);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String url = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyDOpB77o-qH10r-zdqfDVimKKjZ_LEnEP0";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Desserializando a resposta para a classe de modelo
        VisionResponse visionResponse = objectMapper.readValue(response.getBody(), VisionResponse.class);

        // Extraindo os nomes dos objetos localizados
        List<String> objectNames = visionResponse.getResponses().stream()
                .flatMap(res -> res.getLocalizedObjectAnnotations().stream())
                .map(VisionResponse.LocalizedObjectAnnotation::getName)
                .collect(Collectors.toList());

        // Imprimindo os nomes extraídos
        objectNames.forEach(System.out::println);

        // Concatenando todos os nomes em uma única string, separados por vírgulas
        String concatenatedNames = String.join(", ", objectNames);

        // Retornando os nomes como Mono
        return Mono.just(concatenatedNames);
    }
}

