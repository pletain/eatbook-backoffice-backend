package com.eatbook.backoffice.entity;

import com.eatbook.backoffice.entity.base.BaseEntity;
import com.eatbook.backoffice.entity.constant.FileType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "file_metadata")
public class FileMetadata extends BaseEntity {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private FileType type;

    @Column(nullable = false)
    @NotNull
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @NotNull
    private Episode episode;

    @Builder
    public FileMetadata(String id, FileType type, String path, Episode episode) {
        this.id = id;
        this.type = type;
        this.path = path;
        this.episode = episode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMetadata fileMetadata = (FileMetadata) o;
        return id.equals(fileMetadata.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}