package com.bmc.exercise.EmailEmulatorClient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO representation of the response of the email client's example usage
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsageExampleResponseDTO {
    private int status;

    private String body;
}
