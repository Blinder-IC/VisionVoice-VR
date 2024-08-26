package com.blinder.visionvoice.config.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.*;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;

@Configuration
public class FeignInterceptor implements Decoder {

    public static final String API_KEY = "AIzaSyDOpB77o-qH10r-zdqfDVimKKjZ_LEnEP0";
    private static final Logger logger = LoggerFactory.getLogger(FeignInterceptor.class);

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.query("key", API_KEY);
            }
        };
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response.status() >= 200 && response.status() < 300) { // Verifica se é uma resposta de sucesso
            if (response.body() != null) {
                String body = Util.toString(response.body().asReader(Util.UTF_8));

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(body);

                // Extrai a parte desejada da resposta
                JsonNode localizedObjectAnnotations = rootNode.path("responses").get(0).path("localizedObjectAnnotations");

                // Converte a parte extraída em um objeto adequado ao `Type` esperado
                return objectMapper.treeToValue(localizedObjectAnnotations, objectMapper.constructType(type));
            }
        }
        // Se não for uma resposta de sucesso, devemos delegar para um ErrorDecoder
        throw new DecodeException(response.status(), "Falha ao decodificar a resposta", response.request());
    }
}
