package com.eatbook.backoffice.global.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GlobalSuccessCode{
    API_SUCCESS("API_SUCCESS", "요청이 성공적으로 처리되었습니다."),
    ;

    private final String code;
    private final String message;
}
