package com.atoncorp.trustin.demo.controller.app.dto;

import lombok.Data;

@Data
public class UserWithdrawResponse {
    private String userEmail;
    private String withdrawDatetime;

    private String toServerRequest;
    private String fromServerResponse;
}
