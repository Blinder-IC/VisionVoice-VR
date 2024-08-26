package com.blinder.visionvoice.config.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FeignInterceptorError implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(FeignInterceptorError.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = "Corpo da resposta não pôde ser lido";
        try {
            if (response.body() != null) {
                body = Util.toString(response.body().asReader(Util.UTF_8));

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(body);

                JsonNode localizedObjectAnnotations = rootNode.path("responses").get(0).path("localizedObjectAnnotations");

                if (!localizedObjectAnnotations.isMissingNode() && localizedObjectAnnotations.isArray()) {
                    body = localizedObjectAnnotations.toString();
                } else {
                    body = "localizedObjectAnnotations não encontrada na resposta";
                }
            }
        } catch (IOException e) {
            logger.error("Erro ao ler o corpo da resposta: {}", e.getMessage(), e);
        }

        // Log da resposta
        logger.info("Interceptando resposta Feign - Código de Status: {}, Corpo: {}", response.status(), body);

        // Modifica ou trata a resposta conforme necessário
        if (response.status() >= 400 && response.status() <= 499) {
            return new RuntimeException("Erro de cliente: " + body);
        }
        if (response.status() >= 500) {
            return new RuntimeException("Erro de servidor: " + body);
        }
        return new RuntimeException("Resposta inesperada: " + body);
    }
}
