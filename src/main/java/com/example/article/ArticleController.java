package com.example.article;

import com.example.article.dto.ArticleDto;
import com.example.article.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
// 어노테이션 붙이기
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    // POST /articles
    @PostMapping
    // 역직렬화
    // RESTful 한 API 는 행동의 결과로 반영된 자원의 상태를 반환함이 옳다
    public ArticleDto create(@RequestBody ArticleDto dto) {
        return service.createArticle(dto);
    }

    // GET /articles
    @GetMapping
    public List<ArticleDto> readAll() {
        return service.readArticleAll();
    }

    // GET /articles/{id}
    @GetMapping("/{id}")
    public ArticleDto read(@PathVariable("id") Long id) {
        return service.readArticle(id);
    }


    // PUT /articles/{id}
    @PutMapping("/{id}")
    public ArticleDto update(
            @PathVariable("id") Long id,
            // @RequestBody !!!!!
            @RequestBody ArticleDto dto
    ) {
        return service.updateArticle(id, dto);
    }

    // DELETE /articles/{id}
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ) {
        service.deleteArticle(id);
    }

    // GET /articles/page-test
    @GetMapping("/page-test")
    public List<ArticleDto> readPageTest() {
        return service.readArticlePaged();
    }
}
