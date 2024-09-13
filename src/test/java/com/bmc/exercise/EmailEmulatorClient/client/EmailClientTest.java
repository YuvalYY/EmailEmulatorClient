package com.bmc.exercise.EmailEmulatorClient.client;

import com.bmc.exercise.EmailEmulatorClient.dto.EmailMessageDTO;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test class for {@link EmailClient}
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock
public class EmailClientTest {
    private static final String TO_EMAIL = "test.employee@testmail.com";

    private static final String FROM_EMAIL = "some.one@fakemail.com";

    private static final String BODY = "test body";

    @Autowired
    private EmailClient emailClient;

    /**
     * Test case for sending an email message
     */
    @Test
    public void testSendingMessage() {
        EmailMessageDTO lEmailMessageDTO = new EmailMessageDTO();
        lEmailMessageDTO.setToEmail(TO_EMAIL);
        lEmailMessageDTO.setFromEmail(FROM_EMAIL);
        lEmailMessageDTO.setBody(BODY);

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/emailServer/receive"))
                .willReturn(WireMock.aResponse().withStatus(204)));

        ResponseEntity<String> lResponse = emailClient.sendMessage(lEmailMessageDTO).block();

        Assertions.assertNotNull(lResponse);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, lResponse.getStatusCode());
        Assertions.assertNull(lResponse.getBody());

        WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo("/emailServer/receive"))
                .withRequestBody(WireMock.equalToJson("{\"toEmail\":\"test.employee@testmail.com\",\"fromEmail\":\"some.one@fakemail.com\",\"body\":\"test body\"}")));
    }
}
