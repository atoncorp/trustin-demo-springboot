package com.atoncorp.trustin.demo.domain;

import com.atoncorp.trustin.demo.controller.app.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class MfaDomain {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    //MFA Enroll 요청
    public ResponseEntity mfaEnrollRequest(String mfaAddUri, HttpHeaders headers, String userEmail, String notiFactor) throws IOException {
        MfaAddRequest mfaAddRequest = new MfaAddRequest();
        mfaAddRequest.setUserEmail(userEmail);

        String requestBody = objectMapper.writeValueAsString(mfaAddRequest);

        HttpEntity requestToServer = new HttpEntity(requestBody, headers);
        ResponseEntity responseToApp = null;

        try{
            ResponseEntity responseFromServer = restTemplate.exchange(mfaAddUri, HttpMethod.POST, requestToServer, String.class);

            MfaAddResponseDto mfaEnrollResponse = objectMapper.readValue((String)responseFromServer.getBody(), MfaAddResponseDto.class);
            mfaEnrollResponse.setFromServerResponse(responseFromServer.toString());
            mfaEnrollResponse.setToServerRequest(requestToServer.toString());

            responseToApp = ResponseEntity.status(responseFromServer.getStatusCode())
                    .header(responseFromServer.getHeaders().toString())
                    .body(mfaEnrollResponse);

        }catch (HttpClientErrorException exception) {
            responseToApp = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
        return responseToApp;
    }

    //MFA 상태 요청
    public ResponseEntity mfaInfoRequest(String mfaStatusInfoUri, HttpHeaders headers, String mfaUid) throws IOException {
        HttpEntity requestToServer = new HttpEntity(headers);
        ResponseEntity responseToApp = null;

        try{
            ResponseEntity responseFromServer = restTemplate.exchange(mfaStatusInfoUri, HttpMethod.GET, requestToServer, String.class);
            MfaInfoResponseDto mfaInfo = objectMapper.readValue((String) responseFromServer.getBody(), MfaInfoResponseDto.class);
            mfaInfo.setToServerRequest(requestToServer.toString());
            mfaInfo.setFromServerResponse(responseFromServer.toString());

            responseToApp = ResponseEntity.status(responseFromServer.getStatusCode())
                    .header(responseFromServer.getHeaders().toString())
                    .body(mfaInfo);
        }catch (HttpClientErrorException exception) {
            responseToApp = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
        return responseToApp;
    }

    //MFA 폐지 요청
    public ResponseEntity discardMfa(String mfaDiscardUri, HttpHeaders headers, MfaDiscardRequestDto discardRequest) throws IOException {
        String requestBody = objectMapper.writeValueAsString(discardRequest);

        HttpEntity requestToServer = new HttpEntity(requestBody, headers);
        ResponseEntity responseToApp = null;

        try{
            ResponseEntity responseFromServer = restTemplate.exchange(mfaDiscardUri, HttpMethod.DELETE, requestToServer, String.class);

            MfaDiscardResponseDto responseMfaDiscardRespopnse = objectMapper.readValue((String)responseFromServer.getBody(), MfaDiscardResponseDto.class);
            responseMfaDiscardRespopnse.setToServerRequest(requestToServer.toString());
            responseMfaDiscardRespopnse.setFromServerResponse(responseFromServer.toString());

            responseToApp = ResponseEntity.status(responseFromServer.getStatusCode())
                    .header(responseFromServer.getHeaders().toString())
                    .body(responseMfaDiscardRespopnse);

        }catch (HttpClientErrorException exception) {
            responseToApp = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
        return responseToApp;
    }


}
