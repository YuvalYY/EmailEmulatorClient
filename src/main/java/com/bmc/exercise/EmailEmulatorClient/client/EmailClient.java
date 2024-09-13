package com.bmc.exercise.EmailEmulatorClient.client;

import com.bmc.exercise.EmailEmulatorClient.dto.EmailMessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * REST client for sending email messages
 */
@Component
public class EmailClient {
    /**
     * Internal HTTP client
     */
    private final WebClient webClient;

    public EmailClient(WebClient.Builder inWebClientBuilder, @Value("${bmc.exercise.server.port}") int inServerPort) {
        webClient = inWebClientBuilder.baseUrl("http://localhost:" + inServerPort).build();
    }

    /**
     * Sends an email message via a REST client
     *
     * @param inEmailMessageDTO The email message
     * @return The response to the message
     */
    public Mono<ResponseEntity<String>> sendMessage(EmailMessageDTO inEmailMessageDTO) {
        return webClient.post().uri("/emailServer/receive")
                .bodyValue(inEmailMessageDTO).accept(MediaType.TEXT_PLAIN).retrieve().toEntity(String.class);
    }
}
