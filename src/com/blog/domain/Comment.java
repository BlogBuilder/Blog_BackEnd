package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class Comment extends Model<Comment> {
    public static Comment commentDao = new Comment();

    public Map _toJson() {
        Map result = new HashMap();
        for (String key : this._getAttrNames()) {
            result.put(key, this.get(key));
        }
        return result;
    }

    public static List toListJson(List<Comment> commentList) {
        List result = new ArrayList();
        for (Comment comment : commentList) {
            result.add(comment._toJson());
        }
        return result;
    }

}
