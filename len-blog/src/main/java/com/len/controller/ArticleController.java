package com.len.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章管理（后台）
 *
 * @author JamesZBL
 * @email 1146556298@qq.com
 * @date 2018-05-05
 */
@RestController
@RequestMapping("/blog")
public class ArticleController {


    @GetMapping("/articleList")
    public String articleListPage() {
        return "articleList";
    }

    /**
     * 文章列表
     */
    @GetMapping("/header")
    public List<String> showArticles() {
        List<String> list = new ArrayList<>(4);
        list.add("java");
        list.add("架构");
        list.add("Linux");
        list.add("其他");
        return list;
    }
}
