package com.blog.controller;

import com.blog.domain.Subscribe;
import com.blog.utils.RenderUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

import java.util.UUID;

/**
 * Created by qulongjun on 2017/12/11.
 */
public class SubscribeController extends Controller {
    @Clear
    public void create() {
        try {
            Subscribe subscribe = Subscribe.subscribeDao
                    .findFirst("SELECT * FROM `db_article_subscribe` WHERE email='" + getPara("email") + "' AND article_id=" + getPara("article_id"));
            if (subscribe == null) {
                Subscribe entry = new Subscribe();
                String cancel_id = UUID.randomUUID().toString();
                Boolean result = entry
                        .set("email", getPara("email"))
                        .set("cancel_id", cancel_id)
                        .set("article_id", getPara("article_id"))
                        .save();
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
                    .findFirst("SELECT * FROM `db_article_subscribe` WHERE email='" + getPara("email") + "' AND article_id=" + getPara("article_id"));
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
                renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
            } else renderJson(RenderUtils.CODE_EMPTY);
        } catch (Exception e) {
            renderError(500);
        }
    }
}
