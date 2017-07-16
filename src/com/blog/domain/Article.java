package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/15.
 */
public class Article extends Model<Article> {
    public static Article articleDao = new Article();

    public Map _toJson(Boolean isSimple) {
        Map entry = new HashMap();
        for (String key : this._getAttrNames()) {
            if (isSimple && key.equals("content")) {
                continue;
            }
            entry.put(key, this.get(key));
        }
        if (!isSimple)
            entry.put("tag", Tag._toListJson(Tag.tagDao.find("SELECT t.* FROM `db_article_tag` a,`db_tag` t WHERE a.tag_id = t.id AND  a.article_id=" + this.get("id"))));

        entry.put("category", Category.categoryDao.findFirst("SELECT c.* FROM `db_article_category` a, `db_category` c WHERE a.category_id = c.id AND  a.article_id=" + this.get("id"))._toJson());


        switch (this.getInt("type")) {
            case 1:
                entry.put("materials", Material._toListJson(Material.materialDao.find("SELECT * FROM `db_article_material` WHERE article_id=" + this.get("id"))));
                break;
            case 2:
            case 3:
            case 4:
                entry.put("materials", Material.materialDao.findFirst("SELECT * FROM `db_article_material` WHERE article_id=" + this.get("id"))._toJson());
                break;
            case 5:
                break;
        }
        return entry;
    }

    public static List _toListJson(List<Article> articleList, Boolean isSample) {
        List arr = new ArrayList();
        for (Article article : articleList) {
            arr.add(article._toJson(isSample));
        }
        return arr;
    }
}
