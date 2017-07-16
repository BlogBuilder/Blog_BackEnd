package com.blog.controller;

import com.blog.domain.Article;
import com.blog.domain.Comment;
import com.blog.utils.ParaUtils;
import com.blog.utils.RenderUtils;
import com.jfinal.core.Controller;
import com.jfinal.render.Render;

import java.util.Date;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class CommentController extends Controller {
    /**
     * 创建评论
     */
    public void create() {
        try {
            int article_id = getParaToInt("id");
            Article article = Article.articleDao.findById(article_id);
            if (article != null) {
                Comment comment = new Comment();
                comment
                        .set("name", getPara("name"))
                        .set("create_time", ParaUtils.timeFormat.format(new Date()))
                        .set("content", getPara("content"))
                        .set("article_id", getPara("id"))
                        .set("state", 1)
                        .set("parent", getPara("parent"));
                Boolean result = comment.save();
                renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
            }
        } catch (Exception e) {
            renderError(500);
        }
    }
}
