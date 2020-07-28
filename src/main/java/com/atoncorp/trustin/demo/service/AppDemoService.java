package com.atoncorp.trustin.demo.service;

import com.atoncorp.trustin.demo.controller.app.dto.*;
import com.atoncorp.trustin.demo.domain.AuthenticationDomain;
import com.atoncorp.trustin.demo.domain.MfaDomain;
import com.atoncorp.trustin.demo.domain.UserDomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

@Service
public class AppDemoService {
    @Value("${trustin.service.url}")
    String trustinServerUrl;

    @Value("${trustin.service.port}")
    int trustinServerPort;

    @Value("${trustin.sp.appluid}")
    String applUid;

    @Value("${trustin.sp.accesstoken}")
    String accessToken;

    @Value("${trustin.sp.language}")
    String lang;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserDomain userDomain;

    @Autowired
    MfaDomain mfaDomain;

    @Autowired
    AuthenticationDomain authnDomain;

    public ResponseEntity addUser(UserRegRequestDto requestUserDto) throws IOException {
        String userAddUri = trustinServerUrl + ":" + trustinServerPort + "/api/v1/" + applUid + "/user";
        HttpHeaders headers = getHttpHeaders();

        return userDomain.userAddRequest(userAddUri, headers, requestUserDto);
    }

    public ResponseEntity mfaEnroll(UserRegRequestDto requestUserDto, String notiFactor) throws IOException {
        String mfaAddUri = trustinServerUrl + ":" + trustinServerPort + "/api/v1/" + applUid + "/mfa/enroll/" + notiFactor;
        HttpHeaders headers = getHttpHeaders();

        return mfaDomain.mfaEnrollRequest(mfaAddUri, headers, requestUserDto.getUserEmail(), notiFactor);
    }

    public ResponseEntity getMfaInfo(String mfaUid) throws IOException {
        String getMfaInfo = trustinServerUrl + ":" + trustinServerPort + "/api/v1/" + applUid + "/mfa/" + mfaUid;
        HttpHeaders headers = getHttpHeaders();

        return mfaDomain.mfaInfoRequest(getMfaInfo, headers, mfaUid);
    }

    public ResponseEntity requestAuthnToken(RequestMfaAuthn requestMfaAuthn) throws IOException {
        //가상의 HTI 세팅
        String randomHti = DigestUtils.sha256Hex(UUID.randomUUID().toString());
        requestMfaAuthn.setHti(randomHti);

        /**
         * 사용자의 AI Data 제공 여부 세팅
         *
         * 가상의 시나리오로써
         * @atoncorp.com 으로 끝나는 이메일은 user_ai_data = Y  : AI 관련 정보 수집 동의
         * 그 외 이메일은 user_ai_data = N : AI 관련 정보 수집 거부
         */
        if(requestMfaAuthn.getUserEmail().contains("@atoncorp.com")){
            requestMfaAuthn.setUserAiYn("Y");
        }else{
            requestMfaAuthn.setUserAiYn("N");
        }

        String requestAuthnTokenUri = trustinServerUrl + ":" + trustinServerPort + "/api/v1/" + applUid + "/mfa/token";
        HttpHeaders headers = getHttpHeaders();

        return authnDomain.requestAuthnToken(requestAuthnTokenUri, headers, requestMfaAuthn);
    }

    public ResponseEntity requestMfaValid(RequestMfaValid requestMfaValid) throws IOException {
        String requestMfaValidationUri = trustinServerUrl + ":" + trustinServerPort + "/api/v1/" + applUid + "/mfa/validation";
        HttpHeaders headers = getHttpHeaders();

        return authnDomain.requestMfaValid(requestMfaValidationUri, headers, requestMfaValid);
    }

    public ResponseEntity discardMfa(String mfaUid, MfaDiscardRequestDto discardRequest) throws IOException {
        String mfaDiscardUri = trustinServerUrl + ":" + trustinServerPort + "/api/v1/" + applUid + "/mfa/" + mfaUid;
        HttpHeaders headers = getHttpHeaders();

        return mfaDomain.discardMfa(mfaDiscardUri, headers, discardRequest);
    }


    public ResponseEntity withdrawUser(String userEmail) throws IOException {
        String userWithdrawUri = trustinServerUrl + ":" + trustinServerPort + "/api/v1/" + applUid + "/user/" + userEmail;
        HttpHeaders headers = getHttpHeaders();

        return userDomain.withdrawUser(userWithdrawUri, headers);
    }

    public ResponseEntity requestTokenInfo(String mfaUid, String trcd) throws IOException {
        String requestTokenInfoUri = trustinServerUrl + ":" + trustinServerPort + "/api/v1/" + applUid + "/mfa/token/" + mfaUid+ "/" + trcd;
        HttpHeaders headers = getHttpHeaders();

        return authnDomain.requestAuthnToken(requestTokenInfoUri, headers);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Key", accessToken);
        headers.set("Accept-Language", lang);
        return headers;
    }
}
