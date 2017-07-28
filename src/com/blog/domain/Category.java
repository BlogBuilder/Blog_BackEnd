package com.blog.domain;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class Category extends Model<Category> {
    public static Category categoryDao = new Category();

    public Map _toJson() {
        Map entry = new HashMap();
        for (String key : this._getAttrNames()) {
            entry.put(key, this.get(key));
        }
        int count = Article_Category.articleCategoryDao.find("SELECT c.* FROM `db_article_category` c,`db_article` a WHERE c.article_id=a.id AND a.state=1 AND c.category_id=" + this.get("id")).size();
        entry.put("count", count);
        return entry;
    }

    public static List _toListJson(List<Category> categoryList) {
        List arr = new ArrayList();
        for (Category category : categoryList) {
            arr.add(category._toJson());
        }
        return arr;
    }
}
