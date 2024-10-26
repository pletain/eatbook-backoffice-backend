package com.eatbook.backoffice.entity;

import com.eatbook.backoffice.entity.idClass.NovelCategoryId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@IdClass(NovelCategoryId.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "novel_category")
public class NovelCategory {

    @Id
    @Column(name = "novel_id", length = 36)
    private String novelId;

    @Id
    @Column(name = "category_id", length = 36)
    private String categoryId;

    @Column(nullable = false)
    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Novel novel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Builder
    public NovelCategory(String novelId, String categoryId, LocalDateTime createdAt, Novel novel, Category category) {
        this.novelId = novelId;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.novel = novel;
        this.category = category;
    }
}
