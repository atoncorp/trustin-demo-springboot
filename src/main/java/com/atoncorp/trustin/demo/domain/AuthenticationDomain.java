package com.atoncorp.trustin.demo.domain;

import com.atoncorp.trustin.demo.controller.app.dto.RequestMfaAuthn;
import com.atoncorp.trustin.demo.controller.app.dto.RequestMfaValid;
import com.atoncorp.trustin.demo.controller.app.dto.ResponseMfaAuthn;
import com.atoncorp.trustin.demo.controller.app.dto.ResponseMfaValid;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
public class AuthenticationDomain {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity requestAuthnToken(String tokenReuqestUri, HttpHeaders headers, RequestMfaAuthn requestMfaAuthn) throws IOException {
        String requestBody = objectMapper.writeValueAsString(requestMfaAuthn);

        HttpEntity requestToServer = new HttpEntity(requestBody, headers);
        ResponseEntity responseToApp = null;

        try{
            ResponseEntity responseFromServer = restTemplate.exchange(tokenReuqestUri, HttpMethod.POST, requestToServer, String.class);

            ResponseMfaAuthn responseMfaAuthn = objectMapper.readValue((String)responseFromServer.getBody(), ResponseMfaAuthn.class);
            responseMfaAuthn.setToServerRequest(requestToServer.toString());
            responseMfaAuthn.setFromServerResponse(responseFromServer.toString());

            responseToApp = ResponseEntity.status(responseFromServer.getStatusCode())
                    .header(responseFromServer.getHeaders().toString())
                    .body(responseMfaAuthn);

        }catch (HttpStatusCodeException exception) {
            responseToApp = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
        return responseToApp;
    }

    public ResponseEntity requestMfaValid(String mfaValidRequestUri, HttpHeaders headers, RequestMfaValid requestMfaValid) throws IOException {
        String requestBody = objectMapper.writeValueAsString(requestMfaValid);

        HttpEntity requestToServer = new HttpEntity(requestBody, headers);
        ResponseEntity responseToApp = null;

        try{
            ResponseEntity responseFromServer = restTemplate.exchange(mfaValidRequestUri, HttpMethod.POST, requestToServer, String.class);

            ResponseMfaValid responseMfaValid = objectMapper.readValue((String)responseFromServer.getBody(), ResponseMfaValid.class);
            responseMfaValid.setToServerRequest(requestToServer.toString());
            responseMfaValid.setFromServerResponse(responseFromServer.toString());

            responseToApp = ResponseEntity.status(responseFromServer.getStatusCode())
                    .header(responseFromServer.getHeaders().toString())
                    .body(responseMfaValid);

        }catch (HttpStatusCodeException exception) {
            responseToApp = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
        return responseToApp;
    }


    public ResponseEntity requestAuthnToken(String requestTokenInfoUri, HttpHeaders headers) throws IOException {
        HttpEntity requestToServer = new HttpEntity("", headers);
        ResponseEntity responseToApp = null;

        try{
            ResponseEntity responseFromServer = restTemplate.exchange(requestTokenInfoUri, HttpMethod.GET, requestToServer, String.class);

            ResponseMfaAuthn responseMfaAuthn = objectMapper.readValue((String)responseFromServer.getBody(), ResponseMfaAuthn.class);
            responseMfaAuthn.setToServerRequest(requestToServer.toString());
            responseMfaAuthn.setFromServerResponse(responseFromServer.toString());

            responseToApp = ResponseEntity.status(responseFromServer.getStatusCode())
                    .header(responseFromServer.getHeaders().toString())
                    .body(responseMfaAuthn);

        }catch (HttpStatusCodeException exception) {
            responseToApp = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
        return responseToApp;
    }

}
