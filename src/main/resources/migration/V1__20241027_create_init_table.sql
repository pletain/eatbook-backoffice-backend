-- 회원 테이블
CREATE TABLE member (
                        id VARCHAR(36) NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        deleted_at DATETIME NULL,
                        nickname VARCHAR(100) NOT NULL,
                        role ENUM('MEMBER', 'ADMIN') NOT NULL DEFAULT 'MEMBER',
                        profile_image_url VARCHAR(255),
                        last_login DATETIME,
                        password_hash VARCHAR(255) NULL,
                        email VARCHAR(255) NULL,
                        PRIMARY KEY (id)
);

-- 사용자 설정 테이블
CREATE TABLE member_setting (
                                id VARCHAR(36) NOT NULL,
                                created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                font_preference VARCHAR(255),
                                theme_preference VARCHAR(255),
                                tts_speed DECIMAL(2,1) NOT NULL DEFAULT 1.0,
                                PRIMARY KEY (id),
                                FOREIGN KEY (id) REFERENCES member(id)
);

-- 카테고리 테이블
CREATE TABLE category (
                          id VARCHAR(36) NOT NULL,
                          name VARCHAR(50) NOT NULL,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          deleted_at DATETIME NULL,
                          PRIMARY KEY (id)
);

-- 소설 테이블
CREATE TABLE novel (
                       id VARCHAR(36) NOT NULL,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       deleted_at DATETIME NULL,
                       title VARCHAR(255) NOT NULL,
                       cover_image_url VARCHAR(255),
                       summary VARCHAR(1000),
                       view_count INT NOT NULL DEFAULT 0,
                       is_completed BOOLEAN NOT NULL,
                       publication_year INT,
                       PRIMARY KEY (id)
);

-- 작가 테이블
CREATE TABLE author (
                        id VARCHAR(36) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        deleted_at DATETIME NULL,
                        PRIMARY KEY (id)
);

-- 소설-작가 연결 테이블
CREATE TABLE novel_author (
                              novel_id VARCHAR(36) NOT NULL,
                              author_id VARCHAR(36) NOT NULL,
                              created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (novel_id, author_id),
                              FOREIGN KEY (novel_id) REFERENCES novel(id),
                              FOREIGN KEY (author_id) REFERENCES author(id)
);

-- 소설-카테고리 연결 테이블
CREATE TABLE novel_category (
                                novel_id VARCHAR(36) NOT NULL,
                                category_id VARCHAR(36) NOT NULL,
                                created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (novel_id, category_id),
                                FOREIGN KEY (novel_id) REFERENCES novel(id),
                                FOREIGN KEY (category_id) REFERENCES category(id)
);

-- 에피소드 테이블
CREATE TABLE episode (
                         id VARCHAR(36) NOT NULL,
                         created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         deleted_at DATETIME NULL,
                         title VARCHAR(255) NOT NULL,
                         chapter_number INT NOT NULL,
                         upload_date DATETIME,
                         release_status varchar(100) NOT NULL DEFAULT 'PUBLIC',
                         view_count INT NOT NULL DEFAULT 0,
                         novel_id VARCHAR(36) NOT NULL,
                         PRIMARY KEY (id),
                         FOREIGN KEY (novel_id) REFERENCES novel(id),
                         CHECK (release_status IN ('PUBLIC', 'PRIVATE', 'SCHEDULED'))
);

-- 독서 기록 테이블
CREATE TABLE reading_log (
                             id VARCHAR(36) NOT NULL,
                             created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             page_number INT NOT NULL,
                             tts_last_position_seconds TIME NOT NULL,
                             member_id VARCHAR(36) NOT NULL,
                             novel_id VARCHAR(36) NOT NULL,
                             episode_id VARCHAR(36) NOT NULL,
                             PRIMARY KEY (id),
                             FOREIGN KEY (member_id) REFERENCES member(id),
                             FOREIGN KEY (novel_id) REFERENCES novel(id),
                             FOREIGN KEY (episode_id) REFERENCES episode(id),
                             CONSTRAINT unique_member_novel_episode UNIQUE (member_id, novel_id, episode_id)
);

-- 댓글 테이블
CREATE TABLE comment (
                         id VARCHAR(36) NOT NULL,
                         content VARCHAR(300) NOT NULL,
                         created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         deleted_at DATETIME NULL,
                         member_id VARCHAR(36) NOT NULL,
                         episode_id VARCHAR(36) NOT NULL,
                         PRIMARY KEY (id),
                         FOREIGN KEY (member_id) REFERENCES member(id),
                         FOREIGN KEY (episode_id) REFERENCES episode(id)
);

-- 좋아요 테이블
CREATE TABLE favorite (
                          novel_id VARCHAR(36) NOT NULL,
                          member_id VARCHAR(36) NOT NULL,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          PRIMARY KEY (novel_id, member_id),
                          FOREIGN KEY (novel_id) REFERENCES novel(id),
                          FOREIGN KEY (member_id) REFERENCES member(id)
);

-- 북마크 테이블
CREATE TABLE bookmark (
                          novel_id VARCHAR(36) NOT NULL,
                          member_id VARCHAR(36) NOT NULL,
                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          PRIMARY KEY (novel_id, member_id),
                          FOREIGN KEY (novel_id) REFERENCES novel(id),
                          FOREIGN KEY (member_id) REFERENCES member(id)
);

-- 파일 메타데이터 테이블
CREATE TABLE file_metadata (
                               id VARCHAR(36) NOT NULL,
                               created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               type ENUM('TTS', 'SCRIPT') NOT NULL,
                               path VARCHAR(255) NOT NULL,
                               episode_id VARCHAR(36) NOT NULL,
                               PRIMARY KEY (id),
                               FOREIGN KEY (episode_id) REFERENCES episode(id) ON DELETE CASCADE
);

-- 검색 기록 테이블
CREATE TABLE search_log (
                            id VARCHAR(36) NOT NULL,
                            created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            term VARCHAR(255) NOT NULL,
                            PRIMARY KEY (id)
);
