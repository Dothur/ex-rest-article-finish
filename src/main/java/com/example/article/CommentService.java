package com.example.article;

import com.example.article.dto.CommentDto;
import com.example.article.entity.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // TODO 게시글 댓글 전체 조회
    // 반환 타입 이름 인자
    public List<CommentDto> readCommentAll(Long articleId) {
        // articleId 가 id 인 CommentEntity 만 반환
        List<CommentEntity> commentEntityList = commentRepository.findAllByArticleId(articleId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentEntity entity : commentEntityList){
            commentDtoList.add(CommentDto.fromEntity(entity));
        }
        return commentDtoList;
    }

    // TODO 게시글 댓글 수정
    public CommentDto updateComment(Long articleId, Long commentId, CommentDto dto){
        // 요청한 댓글이 존재하는지
        Optional<CommentEntity> optionalComment = commentRepository.findById(commentId);
        // 존재하지 않으면 예외 발생
        if (optionalComment.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // 존재하면 로직 진행
        CommentEntity comment = optionalComment.get();
        // 대상 댓글이 대상 게시글의 댓글이 맞는지
        if (!articleId.equals(comment.getArticleId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        comment.setWriter(dto.getWriter());
        comment.setContent(dto.getContent());

        return CommentDto.fromEntity(commentRepository.save(comment));
    }

    // TODO 게시글 댓글 삭제
    public void deleteComment(Long articleId, Long commentId){
        Optional<CommentEntity> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        CommentEntity comment = optionalComment.get();

        if (!articleId.equals(comment.getArticleId())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else {
            commentRepository.deleteById(commentId);
        }
    }
}
