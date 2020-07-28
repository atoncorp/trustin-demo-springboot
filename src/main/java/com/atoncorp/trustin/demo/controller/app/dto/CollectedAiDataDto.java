package com.atoncorp.trustin.demo.controller.app.dto;

import lombok.Data;

@Data
public class CollectedAiDataDto {
    String osName;
    String osVersion;
    String deviceName;
    String telComName;
    String telComCounty;

    //위도
    String latitude;
    //경도
    String longitude;
}
