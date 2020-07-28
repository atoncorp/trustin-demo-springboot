package com.atoncorp.trustin.demo.controller.app;

import com.atoncorp.trustin.demo.controller.app.dto.MfaDiscardRequestDto;
import com.atoncorp.trustin.demo.controller.app.dto.RequestMfaAuthn;
import com.atoncorp.trustin.demo.controller.app.dto.RequestMfaValid;
import com.atoncorp.trustin.demo.controller.app.dto.UserRegRequestDto;
import com.atoncorp.trustin.demo.service.AppDemoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AppController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AppDemoService demoService;

    //5.1 사용자 등록
    @PostMapping("/demo/app/user")
    public ResponseEntity requestMfaUserReg(@RequestBody UserRegRequestDto requestUserDto) throws IOException {
        return  demoService.addUser(requestUserDto);
    }

    //5.2 MFA 등록
    @PostMapping("/demo/app/mfa")
    public ResponseEntity requestMfaEnroll(@RequestBody UserRegRequestDto requestUserDto) throws IOException {
        String notiFactor = "ALL";
        return  demoService.mfaEnroll(requestUserDto, notiFactor);
    }
    @PostMapping("/demo/app/mfa/{notiFactor}")
    public ResponseEntity requestMfaEnrollWithFactor(@RequestBody UserRegRequestDto requestUserDto, @PathVariable String notiFactor) throws IOException {
        return  demoService.mfaEnroll(requestUserDto, notiFactor);
    }

    //5.3 MFA 등록 상태 조회
    @GetMapping("/demo/app/mfa/{mfaUid}")
    public ResponseEntity requestMfaInfo(@PathVariable String mfaUid) throws IOException {
        return demoService.getMfaInfo(mfaUid);
    }

    //5.4 인증 토큰 생성
    @PostMapping("/demo/app/authn")
    public ResponseEntity requestMfaAuthn(@RequestBody RequestMfaAuthn requestMfaAuthn) throws IOException {
        return demoService.requestAuthnToken(requestMfaAuthn);
    }

    //5.5 인증 값 검증
    @PostMapping("/demo/app/validation")
    public ResponseEntity validationMfaValue(@RequestBody RequestMfaValid requestMfaValid) throws IOException {
        return demoService.requestMfaValid(requestMfaValid);
    }

    //5.6 사용자 탈퇴
    @DeleteMapping("/demo/app/user/{userEmail}")
    public ResponseEntity userWithdrawRight(@PathVariable String userEmail) throws IOException {
        return demoService.withdrawUser(userEmail);
    }

    //5.7 MFA 폐지
    @DeleteMapping("/demo/app/mfa/{mfaUid}")
    public ResponseEntity discardMfaRight(@PathVariable String mfaUid, @RequestBody MfaDiscardRequestDto discardRequest) throws IOException {
        return demoService.discardMfa(mfaUid, discardRequest);
    }


    @DeleteMapping("/demo/app/delete/{messages}")
    public String deleteTest(@PathVariable String messages) {

        return messages;
    }

    @GetMapping("/demo/test/client/{message}")
    public String testClient(@PathVariable String message){
        System.out.println(message);
        return "Hello " + message;
    }


    /*
    곧 업데이트될 API 샘플입니다.
     */
    @GetMapping("/demo/app/token/{mfaUid}/{trcd}")
    public ResponseEntity requestMfaAuthn(@PathVariable String mfaUid, @PathVariable String trcd) throws IOException {
        ResponseEntity responseEntity = demoService.requestTokenInfo(mfaUid, trcd);

        return responseEntity;
    }

}
