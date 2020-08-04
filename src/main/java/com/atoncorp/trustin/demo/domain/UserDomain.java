package com.atoncorp.trustin.demo.domain;

import com.atoncorp.trustin.demo.controller.app.dto.UserRegRequestDto;
import com.atoncorp.trustin.demo.controller.app.dto.UserRegResponseDto;
import com.atoncorp.trustin.demo.controller.app.dto.UserWithdrawResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

@Slf4j
@Service
public class UserDomain {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity userAddRequest(String userAddUri, HttpHeaders headers, UserRegRequestDto userAddDto) throws IOException {
        String requestBody = objectMapper.writeValueAsString(userAddDto);

        HttpEntity requestToServer = new HttpEntity(requestBody, headers);
        ResponseEntity responseToApp = null;

        try{
            ResponseEntity responseFromServer = restTemplate.exchange(userAddUri, HttpMethod.POST, requestToServer, String.class);

            UserRegResponseDto userRegResponseDto = objectMapper.readValue((String)responseFromServer.getBody(), UserRegResponseDto.class);
            userRegResponseDto.setToServerRequest(requestToServer.toString());
            userRegResponseDto.setFromServerResponse(responseFromServer.toString());

            responseToApp = ResponseEntity.status(responseFromServer.getStatusCode())
                    .header(responseFromServer.getHeaders().toString())
                    .body(userRegResponseDto);

        }catch (HttpClientErrorException exception) {
            responseToApp = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
        return responseToApp;
    }

    public ResponseEntity withdrawUser(String userWithdrawUri, HttpHeaders headers) throws IOException {
        HttpEntity requestToServer = new HttpEntity("", headers);
        ResponseEntity responseToApp = null;

        try{
            ResponseEntity responseFromServer = restTemplate.exchange(userWithdrawUri, HttpMethod.DELETE, requestToServer, String.class);

            UserWithdrawResponse userWithdrawResponse = objectMapper.readValue((String)responseFromServer.getBody(), UserWithdrawResponse.class);
            userWithdrawResponse.setToServerRequest(requestToServer.toString());
            userWithdrawResponse.setFromServerResponse(responseFromServer.toString());

            responseToApp = ResponseEntity.status(responseFromServer.getStatusCode())
                    .header(responseFromServer.getHeaders().toString())
                    .body(userWithdrawResponse);

        }catch (HttpClientErrorException exception) {
            responseToApp = ResponseEntity
                    .status(exception.getRawStatusCode())
                    .headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
        }
        return responseToApp;
    }
}
