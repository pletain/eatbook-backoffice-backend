package com.eatbook.backoffice.domain.novel.response;

import com.eatbook.backoffice.global.response.StatusCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum NovelSuccessCode implements StatusCode {
    NOVEL_CREATED("NOVEL_CREATED", "소설이 성공적으로 생성되었습니다."),
    ;

    private final String code;
    private final String message;
}