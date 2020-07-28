package com.atoncorp.trustin.demo.controller.app.dto;

import lombok.Data;

@Data
public class MfaDiscardRequestDto {
    private String userEmail;
    private String discardReason;
}
