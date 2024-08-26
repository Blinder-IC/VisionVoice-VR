package com.blinder.visionvoice.domain.usecase.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class SocketServerService {

    private final RestTemplate restTemplate;

    @Autowired
    public SocketServerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void startServer() {
        new Thread(this::runServer).start();
    }

    private void runServer() {
        int port = 8081;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor socket rodando na porta " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     InputStream inputStream = clientSocket.getInputStream();
                     OutputStream outputStream = clientSocket.getOutputStream()) {

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);

                    HttpEntity<ByteArrayResource> requestEntity = new HttpEntity<>(
                            new ByteArrayResource(imageBytes) {
                                @Override
                                public String getFilename() {
                                    return "image";
                                }
                            }, headers
                    );

                    ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                            "http://localhost:8080/blinder/process",
                            HttpMethod.POST,
                            requestEntity,
                            byte[].class
                    );

                    if (responseEntity.getStatusCode() == HttpStatus.OK) {
                        byte[] audioBytes = responseEntity.getBody();
                        outputStream.write(audioBytes);
                    } else {
                        outputStream.write("Erro ao processar a imagem".getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}