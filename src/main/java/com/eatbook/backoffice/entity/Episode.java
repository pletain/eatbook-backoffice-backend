package com.eatbook.backoffice.entity;

import com.eatbook.backoffice.entity.base.SoftDeletableEntity;
import com.eatbook.backoffice.entity.constant.ReleaseStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "episode")
public class Episode extends SoftDeletableEntity {

    @Id
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    @NotNull
    private String title;

    @Column(nullable = false)
    @NotNull
    private int chapterNumber;

    @Column
    private LocalDateTime uploadDate;

    @Column(nullable = false, length = 100)
    @NotNull
    @Enumerated(EnumType.STRING)
    private ReleaseStatus releaseStatus;

    @Column(nullable = false)
    private int viewCount;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Novel novel;

    @Builder
    public Episode(String title, int chapterNumber, LocalDateTime uploadDate, ReleaseStatus releaseStatus, int viewCount, Novel novel) {
        this.title = title;
        this.chapterNumber = chapterNumber;
        this.uploadDate = uploadDate;
        this.releaseStatus = releaseStatus;
        this.viewCount = viewCount;
        this.novel = novel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Episode episode = (Episode) o;
        return id.equals(episode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

