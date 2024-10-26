package com.eatbook.backoffice.entity;

import com.eatbook.backoffice.entity.idClass.BookmarkId;
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

@IdClass(BookmarkId.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @Column(name = "novel_id", length = 36)
    private String novelId;

    @Id
    @Column(name = "member_id", length = 36)
    private String memberId;

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
    private Member member;

    @Builder
    public Bookmark(String novelId, String memberId, Novel novel, Member member) {
        this.novelId = novelId;
        this.memberId = memberId;
        this.novel = novel;
        this.member = member;
    }
}
