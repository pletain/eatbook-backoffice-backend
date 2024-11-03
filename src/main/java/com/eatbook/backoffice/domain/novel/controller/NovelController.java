package com.eatbook.backoffice.domain.novel.controller;

import com.eatbook.backoffice.domain.novel.dto.NovelRequest;
import com.eatbook.backoffice.domain.novel.dto.NovelResponse;
import com.eatbook.backoffice.domain.novel.service.NovelService;
import com.eatbook.backoffice.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.eatbook.backoffice.domain.novel.response.NovelSuccessCode.NOVEL_CREATED;

/**
 * 소설 관련 작업을 관리하는 컨트롤러.
 *
 * @author lavin
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/novel")
public class NovelController {
    private final NovelService novelService;

    /**
     * 새로운 소설을 생성합니다.
     *
     * @param novelRequest 소설 세부 정보가 포함된 요청 객체.
     * @return HTTP 상태 201 (Created)과 성공 코드와 생성된 소설Id와 커버 이미지용 presigned URL 정보가 포함된 ApiResponse를 포함하는 ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createNovel(@ModelAttribute @Validated NovelRequest novelRequest) {

        log.info("Create Novel Request: {}", novelRequest);

        NovelResponse response = novelService.createNovel(novelRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(NOVEL_CREATED, response));
    }
}