package com.blog.controller;

import com.blog.domain.Category;
import com.blog.domain.Tag;
import com.blog.utils.ParaUtils;
import com.blog.utils.RenderUtils;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

import java.sql.SQLException;
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


    public void adminList() {
        try {
            int rowCount = getParaToInt("rowCount");
            int currentPage = getParaToInt("currentPage");
            String condition_temp = getPara("condition");
            Map condition = ParaUtils.getSplitCondition(condition_temp);
            if (rowCount == 0) {
                rowCount = ParaUtils.getRowCount();
            }
            String paras = "WHERE 1=1 ";
            String tables = " FROM `db_category` c ";
            Object[] keys = condition.keySet().toArray();
            boolean flag = false;
            for (int i = 0; i < keys.length; i++) {
                String key = (String) keys[i];
                Object value = condition.get(key);
                switch (key) {
                    case "key":
                        paras += " AND c.name like \"%" + value + "%\" ";
                        break;
                    case "state":
                        flag = true;
                        paras += " AND c.state=" + value + " ";
                        break;
                }
            }
            if (!flag) {
                paras += " AND c.state=1 ";
            }

            Page<Category> categoryPage = Category.categoryDao.paginate(currentPage, rowCount, "SELECT c.*", tables + paras);
            List<Category> categoryList = categoryPage.getList();
            Map results = new HashMap();
            results.put("results", Category._toListJson(categoryList));
            results.put("currentPage", currentPage);
            results.put("totalPage", categoryPage.getTotalPage());
            results.put("rowCount", rowCount);
            results.put("condition", condition_temp);
            renderJson(results);
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


    /**
     * 查找文章分类
     */
    public void findById() {
        try {
            int id = getParaToInt("id");
            Category category = Category.categoryDao.findFirst("SELECT * FROM `db_category` WHERE state=1 AND id=" + id);
            renderJson(category);
        } catch (Exception e) {
            renderError(500);
        }
    }

    /**
     * 修改文章分类
     */
    public void change() {
        try {
            int id = getParaToInt("id");
            Category category = Category.categoryDao.findById(id);
            if (category != null) {
                category.set("name", getPara("name"));
                renderJson(category.update() ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
            } else renderJson(RenderUtils.CODE_EMPTY);
        } catch (Exception e) {
            renderError(500);
        }
    }


    /**
     * 删除文章分类
     */
    public void delete() {
        try {
            int id = getParaToInt("id");
            Boolean result = Category.categoryDao.deleteById(id);
            renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
        } catch (Exception e) {
            renderError(500);
        }
    }


    /**
     * 批量删除
     */
    public void deleteAll() {
        try {
            Boolean result = Db.tx(new IAtom() {
                @Override
                public boolean run() throws SQLException {
                    Boolean result = true;
                    Integer[] ids = getParaValuesToInt("selected[]");
                    for (int id : ids) {
                        result = result && Category.categoryDao.deleteById(id);
                    }
                    return result;
                }
            });
            renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
        } catch (Exception e) {
            renderError(500);
        }
    }
}
