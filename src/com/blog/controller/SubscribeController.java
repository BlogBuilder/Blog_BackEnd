package com.blog.controller;

import com.blog.domain.Article;
import com.blog.domain.Comment;
import com.blog.domain.Subscribe;
import com.blog.utils.MailPush;
import com.blog.utils.RenderUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.FileKit;
import com.jfinal.kit.PathKit;

import java.util.List;
import java.util.UUID;

/**
 * Created by qulongjun on 2017/12/11.
 */
public class SubscribeController extends Controller {
    @Clear
    public void create() {
        try {
            Subscribe subscribe = Subscribe.subscribeDao
                    .findFirst("SELECT * FROM `db_article_subscribe` WHERE email='" + getPara("email") + "' AND article_id=" + getPara("article_id") + " AND state=1");
            if (subscribe == null) {
                Subscribe entry = new Subscribe();
                String cancel_id = UUID.randomUUID().toString().replace("-", "");
                String activation = UUID.randomUUID().toString().replace("-", "");

                Boolean result = entry
                        .set("email", getPara("email"))
                        .set("cancel_id", cancel_id)
                        .set("article_id", getPara("article_id"))
                        .set("activation", activation)
                        .set("state", 0)
                        .save();
                Article article = Article.articleDao.findById(getPara("article_id"));
                result = result && MailPush.sendActivateEmail(article, entry);
                renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
            } else renderJson(RenderUtils.CODE_NOTEMPTY);
        } catch (Exception e) {
            renderError(500);
        }
    }


    @Clear
    public void findState() {
        try {
            Subscribe subscribe = Subscribe.subscribeDao
                    .findFirst("SELECT * FROM `db_article_subscribe` WHERE email='" + getPara("email") + "' AND article_id=" + getPara("article_id") + " AND state=1");
            renderJson(subscribe != null ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_EMPTY);
        } catch (Exception e) {
            renderError(500);
        }
    }

    @Clear
    public void cancel() {
        try {
            Subscribe subscribe = Subscribe.subscribeDao.findFirst("SELECT * FROM `db_article_subscribe` WHERE cancel_id='" + getPara("cancel_id") + "'");
            if (subscribe != null) {
                Boolean result = subscribe.delete();
                if (result) {
                    render("../../success_cancel.html");
                } else {
                    render("../../error_cancel.html");
                }
            } else render("../../error_cancel.html");
        } catch (Exception e) {
            render("../../error_cancel.html");
        }
    }

    @Clear
    public void newComment() {
        try {
            int article_id = getParaToInt("article_id");
            int comment_id = getParaToInt("comment_id");
            List<Subscribe> subscribeList = Subscribe.subscribeDao.find("SELECT * FROM `db_article_subscribe` WHERE state=1 AND article_id=" + article_id);
            Article article = Article.articleDao.findById(article_id);
            Comment comment = Comment.commentDao.findById(comment_id);
            for (Subscribe subscribe : subscribeList) {
                System.out.printf("给" + subscribe.get("email") + "发了邮件！");
                MailPush.sendCommentEmail(article_id, article.getStr("title"), comment.getStr("name"), comment.getStr("create_time"), comment.getStr("content"), subscribe);
            }
            renderNull();
        } catch (Exception e) {
            renderError(500);
        }
    }

    @Clear
    public void activate() {
        try {
            String activeCode = getPara("activate_id");
            Subscribe subscribe = Subscribe.subscribeDao.findFirst("SELECT * FROM `db_article_subscribe` WHERE activation='" + activeCode + "' AND state=0");
            if (subscribe != null) {
                Boolean result = subscribe.set("state", 1).update();
                if (result) {
                    render("../../success.html");
                } else {
                    render("../../error.html");
                }
            } else {
                render("../../error.html");
            }
        } catch (Exception e) {
            render("../../error.html");
        }
    }
}
