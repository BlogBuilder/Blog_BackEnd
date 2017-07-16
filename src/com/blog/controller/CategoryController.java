package com.blog.controller;

import com.blog.domain.Category;
import com.blog.domain.Tag;
import com.blog.utils.RenderUtils;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class CategoryController extends Controller {
    public void create() {
        try {
            Category category = new Category();
            Boolean result = category
                    .set("name", getPara("name"))
                    .set("state", 1)
                    .save();
            renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
        } catch (Exception e) {
            renderError(500);
        }
    }


    /**
     * 返回全部state为1的分类
     */
    public void list() {
        try {
            List<Category> categoryList = Category._toListJson(Category.categoryDao.find("SELECT * FROM `db_category` WHERE state=1"));
            Map result = new HashMap();
            result.put("results", categoryList);
            renderJson(result);
        } catch (Exception e) {
            renderError(500);
        }
    }
}
