package com.eatbook.backoffice.domain.novel.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NovelResponse(
        @JsonProperty("novelId")
        String novelId,

        @JsonProperty("presignedUrl")
        String preSignedUrl
) {
    public NovelResponse(String novelId, String preSignedUrl) {
        this.novelId = novelId;
        this.preSignedUrl = preSignedUrl;
    }
}
