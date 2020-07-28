package com.atoncorp.trustin.demo.controller.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MfaDiscardResponseDto {
    private String mfaUid;
    private String discardResult;
    private String discardDatetime;

    private String toServerRequest;
    private String fromServerResponse;
}
