package com.atoncorp.trustin.demo.controller.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMfaAuthn {
    private String trcd;
    private String trcdCheckUrl;
    private String qrImageLink;
    private String timeToken;
    private String hti;
    private QRInfo qrInfo;
    private String userAiYn;

    private String toServerRequest;
    private String fromServerResponse;
}
