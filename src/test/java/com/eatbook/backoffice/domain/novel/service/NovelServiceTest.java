package com.eatbook.backoffice.domain.novel.service;

import com.eatbook.backoffice.domain.novel.dto.NovelRequest;
import com.eatbook.backoffice.domain.novel.dto.NovelResponse;
import com.eatbook.backoffice.domain.novel.exception.NovelAlreadyExistsException;
import com.eatbook.backoffice.domain.novel.repository.*;
import com.eatbook.backoffice.entity.*;
import com.eatbook.backoffice.entity.constant.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static com.eatbook.backoffice.domain.novel.response.NovelErrorCode.NOVEL_ALREADY_EXISTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NovelServiceTest {

    @InjectMocks
    private NovelService novelService;

    @Mock
    private NovelRepository novelRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private NovelCategoryRepository novelCategoryRepository;
    @Mock
    private NovelAuthorRepository novelAuthorRepository;
    @Mock
    private FileService fileService;

    @Test
    void shouldThrowNovelAlreadyExistsExceptionWhenTryingToCreateNovelWithSameTitleAndAuthor() {
        // given
        NovelRequest novelRequest = NovelRequest.builder()
                .title("Same Title")
                .author("Same Author")
                .summary("Valid Summary")
                .category(List.of("Valid Category"))
                .isCompleted(true)
                .publicationYear(1800)
                .build();

        when(novelAuthorRepository.findByNovelTitleAndAuthorName("Same Title", "Same Author"))
                .thenReturn(Optional.of(NovelAuthor.builder().build()));

        // when
        NovelAlreadyExistsException exception = assertThrows(NovelAlreadyExistsException.class, () -> novelService.createNovel(novelRequest));

        // then
        assertThat(exception.getErrorCode()).isEqualTo(NOVEL_ALREADY_EXISTS);

        // Repository와 관련된 save 메서드가 호출되지 않았는지 확인
        verify(novelRepository, never()).save(any(Novel.class));
        verify(authorRepository, never()).save(any(Author.class));
        verify(categoryRepository, never()).save(any(Category.class));
        verify(novelCategoryRepository, never()).save(any(NovelCategory.class));

        // novelAuthorRepository의 메서드가 한 번만 호출되었는지 확인
        verify(novelAuthorRepository, times(1)).findByNovelTitleAndAuthorName(anyString(), anyString());

        // 파일 서비스 관련 메서드가 호출되지 않았는지 확인
        verify(fileService, never()).getPresignUrl(anyString(), any(ContentType.class), anyString());
    }

    @Test
    void shouldCreateNovelSuccessfullyWhenAllInputsAreValid() {
        // given
        NovelRequest novelRequest = NovelRequest.builder()
                .title("Valid Title")
                .author("Valid Author")
                .summary("Valid Summary")
                .category(List.of("Valid Category"))
                .isCompleted(true)
                .publicationYear(1800)
                .build();

        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        when(categoryRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        when(novelRepository.save(any(Novel.class)))
                .thenAnswer(invocation -> {
                    Novel novel = invocation.getArgument(0);
                    Novel novelWithId = createNovelWithId("test-id", novel.getTitle(), novel.getSummary(), novel.getPublicationYear());
                    return novelWithId;
                });

        when(authorRepository.save(any(Author.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(categoryRepository.save(any(Category.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(novelCategoryRepository.save(any(NovelCategory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(novelAuthorRepository.save(any(NovelAuthor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(fileService.getPresignUrl(any(), any(ContentType.class), anyString()))
                .thenReturn("presignedUrl");

        // When
        NovelResponse novelResponse = novelService.createNovel(novelRequest);

        // Then
        assertThat(novelResponse.novelId()).isNotNull();
        assertThat(novelResponse.preSingedUrl()).isEqualTo("presignedUrl");

        // Repository와 관련된 save 메서드가 1번씩 호출되었는지 확인
        verify(novelRepository, times(1)).save(any(Novel.class));
        verify(authorRepository, times(1)).save(any(Author.class));
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(novelCategoryRepository, times(1)).save(any(NovelCategory.class));
        verify(novelAuthorRepository, times(1)).save(any(NovelAuthor.class));

        // 파일 서비스 관련 메서드가 한 번만 호출되었는지 확인
        verify(fileService, times(1)).getPresignUrl(anyString(), any(ContentType.class), anyString());
    }

    @Test
    void shouldCreateNovelSuccessfullyWhenTitleIsSameButAuthorIsDifferent() {
        // given

        NovelRequest novelRequest = NovelRequest.builder()
                .title("Common Title")
                .author("New Author")
                .summary("Summary")
                .category(List.of("Category"))
                .isCompleted(true)
                .publicationYear(1800)
                .build();

        when(novelAuthorRepository.findByNovelTitleAndAuthorName("Common Title", "New Author"))
                .thenReturn(Optional.empty()); // 새로운 작가의 경우 같은 제목의 소설이 없음

        when(authorRepository.findByName("New Author"))
                .thenReturn(Optional.empty()); // 새로운 작가 생성

        when(categoryRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        when(novelRepository.save(any(Novel.class)))
                .thenAnswer(invocation -> {
                    Novel novel = invocation.getArgument(0);
                    return createNovelWithId("test-id", novel.getTitle(), novel.getSummary(), novel.getPublicationYear());
                });

        when(authorRepository.save(any(Author.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(categoryRepository.save(any(Category.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(novelCategoryRepository.save(any(NovelCategory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(novelAuthorRepository.save(any(NovelAuthor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(fileService.getPresignUrl(any(), any(ContentType.class), anyString()))
                .thenReturn("presignedUrl");

        // When
        NovelResponse novelResponse = novelService.createNovel(novelRequest);

        // Then
        assertThat(novelResponse.novelId()).isNotNull();
        assertThat(novelResponse.preSingedUrl()).isEqualTo("presignedUrl");

        // Repository와 관련된 save 메서드가 호출되었는지 확인
        verify(novelRepository, times(1)).save(any(Novel.class));
        verify(authorRepository, times(1)).save(any(Author.class));
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(novelCategoryRepository, times(1)).save(any(NovelCategory.class));
        verify(novelAuthorRepository, times(1)).save(any(NovelAuthor.class));

        // 파일 서비스 관련 메서드가 한 번만 호출되었는지 확인
        verify(fileService, times(1)).getPresignUrl(anyString(), any(ContentType.class), anyString());
    }

    @Test
    void shouldCreateNovelSuccessfullyWhenCategoryAlreadyExists() {
        // given
        String existingCategoryName = "Existing Category";
        Category existingCategory = Category.builder()
                .name(existingCategoryName)
                .build();

        NovelRequest novelRequest = NovelRequest.builder()
                .title("Valid Title")
                .author("Valid Author")
                .summary("Summary")
                .category(List.of(existingCategoryName, "New Category"))
                .isCompleted(true)
                .publicationYear(1800)
                .build();

        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.empty());

        when(categoryRepository.findByName(existingCategoryName))
                .thenReturn(Optional.of(existingCategory));

        when(categoryRepository.findByName("New Category"))
                .thenReturn(Optional.empty());

        when(novelRepository.save(any(Novel.class)))
                .thenAnswer(invocation -> {
                    Novel novel = invocation.getArgument(0);
                    return createNovelWithId("test-id", novel.getTitle(), novel.getSummary(), novel.getPublicationYear());
                });

        when(authorRepository.save(any(Author.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(categoryRepository.save(any(Category.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(novelCategoryRepository.save(any(NovelCategory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(novelAuthorRepository.save(any(NovelAuthor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(fileService.getPresignUrl(any(), any(ContentType.class), anyString()))
                .thenReturn("presignedUrl");

        // When
        NovelResponse novelResponse = novelService.createNovel(novelRequest);

        // Then
        assertThat(novelResponse.novelId()).isNotNull();
        assertThat(novelResponse.preSingedUrl()).isEqualTo("presignedUrl");

        // Repository와 관련된 save 메서드가 1번씩 호출되었는지 확인
        verify(novelRepository, times(1)).save(any(Novel.class));
        verify(authorRepository, times(1)).save(any(Author.class));
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(novelCategoryRepository, times(2)).save(any(NovelCategory.class)); // 기존 및 새 카테고리 각각 저장
        verify(novelAuthorRepository, times(1)).save(any(NovelAuthor.class));

        // 파일 서비스 관련 메서드가 한 번만 호출되었는지 확인
        verify(fileService, times(1)).getPresignUrl(anyString(), any(ContentType.class), anyString());
    }


    // 헬퍼 메서드: 테스트용 Novel ID 설정
    public static Novel createNovelWithId(String id, String title, String summary, int publicationYear) {
        Novel novel = Novel.builder()
                .title(title)
                .summary(summary)
                .publicationYear(publicationYear)
                .build();

        // 리플렉션을 사용하여 id 설정
        try {
            Field idField = Novel.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(novel, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return novel;
    }
}