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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/novel")
public class NovelController {

    private final NovelService novelService;

    @PostMapping
    public ResponseEntity<ApiResponse> createNovel(@ModelAttribute @Validated NovelRequest novelRequest) {

        log.info("Create Novel Request: {}", novelRequest);

        NovelResponse response = novelService.createNovel(novelRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.of(NOVEL_CREATED, response));
    }
}