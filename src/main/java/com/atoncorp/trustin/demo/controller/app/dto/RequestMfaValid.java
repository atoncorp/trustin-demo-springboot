package com.atoncorp.trustin.demo.controller.app.dto;

import lombok.Data;

@Data
public class RequestMfaValid {
    private String applUid;
    private String trcd;
    private String userEmail;
    private String mfaUid;
    private String otpValue;
    
    //AI 정보
    private String aiData;
}
