package com.bmc.exercise.EmailEmulatorClient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO representation of an email message
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmailMessageDTO {
    private String toEmail;

    private String fromEmail;

    private String body;
}
