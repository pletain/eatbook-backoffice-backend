package com.eatbook.backoffice.entity;

import com.eatbook.backoffice.entity.base.SoftDeletableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends SoftDeletableEntity {

    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 300)
    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Member member;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Episode episode;

    @Builder
    public Comment(String content, Member member, Episode episode) {
        this.content = content;
        this.member = member;
        this.episode = episode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
