package com.blog.config;

import com.blog.controller.*;

import com.blog.domain.Comment;
import com.jfinal.config.Routes;

/**
 * 前端页面路由
 */
public class FrontRoutes extends Routes {
    @Override
    public void config() {
        add("/api/article", ArticleController.class);
        add("/api/tag", TagController.class);
        add("/api/category", CategoryController.class);
        add("/api/author", AuthorController.class);
        add("/api/comment", CommentController.class);
    }
}
