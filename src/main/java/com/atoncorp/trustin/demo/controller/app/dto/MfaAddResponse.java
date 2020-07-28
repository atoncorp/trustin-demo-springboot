package com.atoncorp.trustin.demo.controller.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MfaAddResponse {
    private String userEmail;
    private String trcd;
    private String trcdCheckUrl;
    private String qrImageLink;
}
