package com.eatbook.backoffice.entity.idClass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookmarkId implements Serializable {
    private static final long serialVersionUID = 3L;

    private String novelId;
    private String memberId;

}
