package com.atoncorp.trustin.demo.controller.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MfaInfoResponseDto {
    private String mfaUid;
    private int taId;
    private String mfaCreateKey;
    private String mfaCryptoKey;
    private LocalDateTime issueDatetime;
    private String mfaStatus;
    private String discardReason;
    private String discardDatetime;
    private String lockDatetime;
    private String lastChangeDatetime;
    private String applUid;
    private String userUid;
    private String deviceUid;
    private String trid;

    //TODO SP에 전달해줄때 사용자 Email 도 전달 or 사용자 정보 get uri 전송?
    private String userEmail;

    private String toServerRequest;
    private String fromServerResponse;
}
