## Trustin 데모 프로젝트

Trustin 데모 프로젝트는 Trustin 서비스를 제공받는 고객의 개발 편의성을 위해 Trustin API 서버의 Restful API를 이용하여 개발되었습니다.

Trustin 데모 프로젝트는 Trustin 서비스의 SDK를 이용하여 구축한 사용자의 단말과 Trustin API 서버 사이에서 메시지의 생성 및 전달하는 역할을 합니다.

------

## 데모 프로젝트 구성

1. Trustin demo는 Springboot 프레임워크 2.1.7을 기반으로 구현되어있습니다.
2. Trustin demo 프로젝트의 빌드는 gradle을 기반으로 구성되어있습니다.
3. Springboot 이외 참조 라이브러리는 다음과 같습니다.
   - lombok
   - apache-common-lang, apache-common-codec

------

## Trustin demo 를 빌드 방법

1. 데모 프로젝트를 clone 하여 개발 PC에 소스를 내려받습니다.

2. 다운받은 소스에서 다음과 같은 명령어로 프로젝트를 빌드합니다.

   gradle clean build

3. 빌드가 성공적으로 끝아면 build/libs 에 trustin-demo-springboot-1.0-RELEASE.jar 파일이 생성됩니다.

------

## 데모 프로젝트 설정 방법

Trustin demo 프로젝트의 설정은 src/main/resources/application.yml 에 설정을 수정할 수 있습니다.

주요 설정은 다음과 같습니다.

```
trustin:
  service:
    url: https://api.truston.io		#Trustin API 서버의 URL
    port: 443				#Trustin API 서버 포트
  sp:
    appluid: 9611037ae9d55dd760b93198fa8db909528fe55dd83g		#Trustin Console에서 어플리케이션 등록 후 발급되는 어플리케이션 ID
    accesstoken: w6KgJgevlM4XIFHh2fWOi50GBMzqzF0K3dEBclv4		#Trustin Console에서 어플리케이션 등록 후 발급되는 API Secret Key
    language: en		#응답 메시지의 언어 en : 영어, kr : 한글
```

------

## 단말 (Android OS) 앱 샘플 소스

Trustin SDK를 이용하여 고객의 앱에 Trustin Service를 적용하는 샘플 소스는 다음 링크에서 다운받으실수 있습니다.

[Trustin Sample APP Download](https://s3.ap-northeast-2.amazonaws.com/static.truston.io/trustin_sample_app/trustinOTP.zip)


------



## 프로젝트 문의

문의 사항이 있으신 경우 trustin@atoncrop.com으로 문의 메일 부탁드립니다.