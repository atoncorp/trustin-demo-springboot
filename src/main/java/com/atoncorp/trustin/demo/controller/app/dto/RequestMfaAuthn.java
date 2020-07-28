package com.atoncorp.trustin.demo.controller.app.dto;

import lombok.Data;

@Data
public class RequestMfaAuthn {
    private String userEmail;
    private String hti;
    private String authnReason;
    
    //사용자의 AI 정보 제공 여부
    private String userAiYn;
}
