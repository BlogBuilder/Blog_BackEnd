package com.blog.controller;

import com.blog.domain.Project_Category;
import com.blog.utils.RenderUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class ProjectCategoryController extends Controller {


    public void create() {
        try {
            Project_Category category = new Project_Category();
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
     * 返回全部分类
     */
    @Clear
    public void list() {
        try {
            List<Project_Category> categoryList = Project_Category._toListJson(Project_Category.projectCategory.find("SELECT * FROM `db_project_category`"));
            Map result = new HashMap();
            result.put("results", categoryList);
            renderJson(result);
        } catch (Exception e) {
            renderError(500);
        }
    }

}
