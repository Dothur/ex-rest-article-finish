package com.example.article;

import com.example.article.dto.CommentDto;
import com.example.article.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public CommentDto createComment(Long articleId, CommentDto dto){
        // articleId 를 id 로 가진 ArticleEntity 가 존재하는지?
        if (!articleRepository.existsById(articleId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        CommentEntity newComment = new CommentEntity();
        newComment.setWriter(dto.getWriter());
        newComment.setContent(dto.getContent());
        newComment.setArticleId(articleId);

        return CommentDto.fromEntity(this.commentRepository.save(newComment));
    }

}
