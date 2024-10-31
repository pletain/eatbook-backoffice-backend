package com.eatbook.backoffice.global.exception.exceptions;
import com.eatbook.backoffice.global.response.StatusCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 비즈니스 관련 오류를 위한 사용자 정의 예외 클래스.
 * RuntimeException 확장하여 언체크 예외를 허용합니다.
 *
 * @author lavin
 * @since 1.0
 */
@Getter
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7L;
    private final StatusCode errorCode;

    /**
     * 지정된 메시지와 오류 코드로 새 BusinessException 생성합니다.
     *
     * @param message 세부 메시지
     * @param errorCode 이 예외와 관련된 특정 오류 코드.
     */
    public BusinessException(String message, StatusCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 오류 코드의 메시지와 지정된 오류 코드로 새 BusinessException 생성합니다.
     *
     * @param errorCode 이 예외와 관련된 특정 오류 코드.
     */
    public BusinessException(StatusCode errorCode) {
        this(errorCode.getMessage(), errorCode);
    }
}