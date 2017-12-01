package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class Project_Category extends Model<Project_Category> {
    public static Project_Category projectCategory = new Project_Category();


    public Map _toJson() {
        Map entry = new HashMap();
        for (String key : this._getAttrNames()) {
            entry.put(key, this.get(key));
        }
        return entry;
    }

    public static List _toListJson(List<Project_Category> categoryList) {
        List arr = new ArrayList();
        for (Project_Category category : categoryList) {
            arr.add(category._toJson());
        }
        return arr;
    }
}
