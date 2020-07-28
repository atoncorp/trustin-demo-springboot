package com.atoncorp.trustin.demo.controller.app.dto;

import lombok.Data;

@Data
public class ErrorMessagesWithUserAdd {
    private String errorCode;
    private String errorMessage;
    private String fromServerResponse;
    private String toServerRequest;
}
