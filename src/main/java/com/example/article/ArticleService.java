package com.example.article;

import com.example.article.dto.ArticleDto;
import com.example.article.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleDto createArticle(ArticleDto dto) {
        ArticleEntity newArticle = new ArticleEntity();
        newArticle.setWriter(dto.getWriter());
        newArticle.setTitle(dto.getTitle());
        newArticle.setContent(dto.getContent());
        return ArticleDto.fromEntity(repository.save(newArticle));
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    public ArticleDto readArticle(Long id) {
        Optional<ArticleEntity> optionalArticleEntity = repository.findById(id);
        if (optionalArticleEntity.isPresent()) {
            return ArticleDto.fromEntity(optionalArticleEntity.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    public List<ArticleDto> readArticleAll() {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        for (ArticleEntity articleEntity : this.repository.findAll()) {
            articleDtoList.add(ArticleDto.fromEntity(articleEntity));
        }
        return articleDtoList;
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    public ArticleDto updateArticle(Long id, ArticleDto dto) {
        Optional<ArticleEntity> optionalArticleEntity = repository.findById(id);
        if (optionalArticleEntity.isPresent()) {
            ArticleEntity targetEntity = optionalArticleEntity.get();
            targetEntity.setWriter(dto.getWriter());
            targetEntity.setTitle(dto.getTitle());
            targetEntity.setContent(dto.getContent());
            return ArticleDto.fromEntity(repository.save(targetEntity));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    public void deleteArticle(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

//        public List<ArticleDto> readArticlePaged() {
//        // JPA Query Method 방식 (비추)
//        List<ArticleDto> articleDtoList = new ArrayList<>();
//        for (ArticleEntity entity : this.repository.findTop20ByOrderByIdDesc()){
//            articleDtoList.add(ArticleDto.fromEntity(entity));
//        }
//        return articleDtoList;
//    }
    public List<ArticleDto> readArticlePaged() {
        // PagingAndSortingRepository 메소드에 전달하는 용도
        // 조회하고 싶은 페이지의 정보를 담는 객체
        // 20개씩 데이터를 나눌 때 0번 페이지를 달라고 요청하는 기준 Pageable
        Pageable pageable = PageRequest.of(0, 20);
        Page<ArticleEntity> articleEntityPage = repository.findAll(pageable);

        List<ArticleDto> articleDtoList = new ArrayList<>();
        for (ArticleEntity entity : articleEntityPage) {
            articleDtoList.add(ArticleDto.fromEntity(entity));
        }
        return articleDtoList;
    }
}
