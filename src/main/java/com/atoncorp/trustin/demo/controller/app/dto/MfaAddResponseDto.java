package com.atoncorp.trustin.demo.controller.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MfaAddResponseDto {
    private String userEmail;
    private String trcd;
    private String trcdCheckUrl;
    private String qrImageLink;

    private String toServerRequest;
    private String fromServerResponse;
}
