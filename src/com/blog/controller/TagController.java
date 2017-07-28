package com.blog.controller;

import com.blog.domain.Tag;
import com.blog.utils.ParaUtils;
import com.blog.utils.RenderUtils;
import com.jfinal.aop.Clear;
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
    @Clear
    public void list() {
        try {
            List<Tag> tagList = Tag._toListJson(Tag.tagDao.find("SELECT * FROM `db_tag` t WHERE state=1"));
            Map result = new HashMap();
            result.put("results", tagList);
            renderJson(result);
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
            String tables = " FROM `db_tag` t ";
            Object[] keys = condition.keySet().toArray();
            boolean flag = false;
            for (int i = 0; i < keys.length; i++) {
                String key = (String) keys[i];
                Object value = condition.get(key);
                switch (key) {
                    case "key":
                        paras += " AND t.name like \"%" + value + "%\" ";
                        break;
                    case "state":
                        flag = true;
                        paras += " AND t.state=" + value + " ";
                        break;
                }
            }
            if (!flag) {
                paras += " AND t.state=1 ";
            }

            Page<Tag> tagPage = Tag.tagDao.paginate(currentPage, rowCount, "SELECT t.*", tables + paras);
            List<Tag> tagList = tagPage.getList();
            Map results = new HashMap();
            results.put("results", Tag._toListJson(tagList));
            results.put("currentPage", currentPage);
            results.put("totalPage", tagPage.getTotalPage());
            results.put("rowCount", rowCount);
            results.put("condition", condition_temp);
            renderJson(results);
        } catch (Exception e) {
            renderError(500);
        }
    }


    /**
     * 查找文章标签
     */
    @Clear
    public void findById() {
        try {
            int id = getParaToInt("id");
            Tag tag = Tag.tagDao.findFirst("SELECT * FROM `db_tag` WHERE state=1 AND id=" + id);
            renderJson(tag);
        } catch (Exception e) {
            renderError(500);
        }
    }

    /**
     * 修改文章标签
     */
    public void change() {
        try {
            int id = getParaToInt("id");
            Tag tag = Tag.tagDao.findById(id);
            if (tag != null) {
                tag.set("name", getPara("name"));
                renderJson(tag.update() ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
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
            Boolean result = Tag.tagDao.deleteById(id);
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
                        result = result && Tag.tagDao.deleteById(id);
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
