package com.blinder.visionvoice.infrastructure.adapter.in.websocket;

import com.blinder.visionvoice.application.services.OcrTtsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.BinaryMessage;
import reactor.core.publisher.Mono;

import java.io.IOException;

@AllArgsConstructor
@Component
public class OcrWebSocketHandler extends BinaryWebSocketHandler {

    @Autowired
    private OcrTtsService ocrTtsService;

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
        byte[] imageBytes = message.getPayload().array();

        ocrTtsService.processImage(imageBytes)
                .doOnNext(audioBytes -> {
                    try {
                        session.sendMessage(new BinaryMessage(audioBytes));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .block(); // Blocking for simplicity in this example
    }
}
