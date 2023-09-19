package com.wellsbabo.springbootblog.service;

import com.wellsbabo.springbootblog.domain.Article;
import com.wellsbabo.springbootblog.dto.AddArticleRequest;
import com.wellsbabo.springbootblog.dto.UpdateArticleRequest;
import com.wellsbabo.springbootblog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor    //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service    //빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }

    //블로그 모든 글 조회
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    //블로그 특정 글 조회
    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    //블로그 특정 글 삭제
    public void delete(long id){
        blogRepository.deleteById(id);
    }

    //블로그 특정 글 수정
    @Transactional  //트랜젝션 메서드
    public Article update(long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
