package com.atoncorp.trustin.demo.controller.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMfaValid {
    private String mfaUid;
    private String trcd;
    private String otpResult;
    private String warnScore;

    private String toServerRequest;
    private String fromServerResponse;
}
