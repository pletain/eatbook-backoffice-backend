package com.eatbook.backoffice.entity;

import com.eatbook.backoffice.entity.idClass.NovelAuthorId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@IdClass(NovelAuthorId.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "novel_author")
public class NovelAuthor {

    @Id
    @Column(name = "novel_id", length = 36)
    @NotNull
    private String novelId;

    @Id
    @Column(name = "author_id", length = 36)
    @NotNull
    private String authorId;

    @Column(nullable = false)
    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @NotNull
    private Novel novel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @NotNull
    private Author author;

    @Builder
    public NovelAuthor(String novelId, String authorId, LocalDateTime createdAt, Novel novel, Author author) {
        this.novelId = novelId;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.novel = novel;
        this.author = author;
    }

}
