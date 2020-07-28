package com.atoncorp.trustin.demo.controller.app.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class UserRegResponseDto {
    private String userEmail;
    private String registDatetime;


    private String toServerRequest;
    private String fromServerResponse;

    @Builder
    public UserRegResponseDto(String userEmail, LocalDateTime registDatetime) {
        this.userEmail = userEmail;
        this.registDatetime = registDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); //TODO 국제 시간 리턴방법 확인
    }
}
