package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class Project_Belongs_Category extends Model<Project_Belongs_Category> {
    public static Project_Belongs_Category projectBelongsCategoryDao = new Project_Belongs_Category();


    public Map _toJson() {
        Map entry = new HashMap();
        entry.put("id", this.get("id"));
        entry.put("name", this.get("name"));
        return entry;
    }

    public static List<Map> _toJSONList(List<Project_Belongs_Category> categories) {
        List<Map> mapList = new ArrayList<>();
        for (Project_Belongs_Category category : categories) {
            mapList.add(category._toJson());
        }
        return mapList;
    }


}
