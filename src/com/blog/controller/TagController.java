package com.blog.controller;

import com.blog.domain.Tag;
import com.blog.utils.RenderUtils;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class TagController extends Controller {
    public void create() {
        try {
            Tag tag = new Tag();
            Boolean result = tag
                    .set("name", getPara("name"))
                    .set("state", 1)
                    .save();
            renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
        } catch (Exception e) {
            renderError(500);
        }
    }

    /**
     * 返回全部state为1的标签
     */
    public void list() {
        try {
            List<Tag> tagList = Tag._toListJson(Tag.tagDao.find("SELECT * FROM `db_tag` WHERE state=1"));
            Map result = new HashMap();
            result.put("results", tagList);
            renderJson(result);
        } catch (Exception e) {
            renderError(500);
        }
    }
}
