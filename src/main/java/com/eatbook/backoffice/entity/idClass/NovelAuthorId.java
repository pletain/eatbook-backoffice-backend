package com.eatbook.backoffice.entity.idClass;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NovelAuthorId implements Serializable {
    private String novelId;
    private String authorId;

}
