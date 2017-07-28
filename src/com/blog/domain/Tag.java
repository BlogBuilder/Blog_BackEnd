package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class Tag extends Model<Tag> {
    public static Tag tagDao = new Tag();

    public Map _toJson() {
        Map entry = new HashMap();
        for (String key : this._getAttrNames()) {
            entry.put(key, this.get(key));
        }
        int count = Article_Tag.articleTagDao.find("SELECT t.* FROM `db_article_tag` t,`db_article` a WHERE t.article_id=a.id AND a.state=1 AND t.tag_id=" + this.get("id")).size();
        entry.put("count", count);
        return entry;
    }

    public static List _toListJson(List<Tag> tagList) {
        List arr = new ArrayList();
        for (Tag tag : tagList) {
            arr.add(tag._toJson());
        }
        return arr;
    }
}
