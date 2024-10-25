package com.eatbook.backoffice.entity.idClass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NovelCategory implements Serializable {
    private String novelId;
    private String categoryId;

}
