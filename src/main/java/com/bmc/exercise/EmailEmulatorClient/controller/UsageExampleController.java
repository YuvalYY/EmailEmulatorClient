package com.bmc.exercise.EmailEmulatorClient.controller;

import com.bmc.exercise.EmailEmulatorClient.client.EmailClient;
import com.bmc.exercise.EmailEmulatorClient.dto.EmailMessageDTO;
import com.bmc.exercise.EmailEmulatorClient.dto.UsageExampleResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Controller that shows example usage of {@link EmailClient}
 */
@RestController
@RequestMapping("/emailClient")
public class UsageExampleController {
    /**
     * Response body in case the REST client didn't return a response
     */
    private static final String DEFAULT_BODY = "No response from server";

    private static final Logger LOGGER = LogManager.getLogger(UsageExampleController.class);

    private final EmailClient emailClient;

    @Autowired
    public UsageExampleController(EmailClient inEmailClient) {
        emailClient = inEmailClient;
    }

    /**
     * Endpoint for receiving an email message to send from the client to the server
     *
     * @param inEmailMessageDTO The email message
     */
    @PostMapping("/send")
    public UsageExampleResponseDTO sendMessage(@RequestBody EmailMessageDTO inEmailMessageDTO) {
        LOGGER.debug("Sending message {}", inEmailMessageDTO);
        UsageExampleResponseDTO outResponseDTO = new UsageExampleResponseDTO();

        try {
            Optional<ResponseEntity<String>> lClientResponseOptional = emailClient.sendMessage(inEmailMessageDTO).blockOptional();
            lClientResponseOptional.ifPresentOrElse(inClientResponse -> {
                outResponseDTO.setStatus(inClientResponse.getStatusCode().value());
                outResponseDTO.setBody(inClientResponse.getBody());
            }, () -> {
                outResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
                outResponseDTO.setBody(DEFAULT_BODY);
            });
        }
        catch (WebClientResponseException inEx) {
            outResponseDTO.setStatus(inEx.getStatusCode().value());
            outResponseDTO.setBody(inEx.getResponseBodyAsString(Charset.defaultCharset()));
            LOGGER.error("Error received from server with status {} and body {}",
                    outResponseDTO.getStatus(), outResponseDTO.getBody());
        }
        catch (WebClientRequestException inEx) {
            outResponseDTO.setStatus(HttpStatus.NOT_FOUND.value());
            outResponseDTO.setBody(DEFAULT_BODY);
            LOGGER.error("Error when attempting to reach the server: {}", inEx.getMessage());
        }

        return outResponseDTO;
    }
}
