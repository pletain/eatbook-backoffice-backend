package com.eatbook.backoffice.domain.novel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NovelResponse(
        @JsonProperty("novelId")
        String novelId,

        @JsonProperty("presingedUrl")
        String preSingedUrl
) {
    public NovelResponse(String novelId, String preSingedUrl) {
        this.novelId = novelId;
        this.preSingedUrl = preSingedUrl;
    }
}
