package com.wellsbabo.springbootblog.controller;

import com.wellsbabo.springbootblog.domain.Article;
import com.wellsbabo.springbootblog.dto.ArticleListViewResponse;
import com.wellsbabo.springbootblog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping(value = "/articles")
    public String getArticles(Model model){
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new)
                .toList();

        System.out.println(articles);
        model.addAttribute("articles", articles);   // 블로그 글 리스트 저장
        
        return "articleList";   // articleList.html 이라는 뷰 조회
    }
}
