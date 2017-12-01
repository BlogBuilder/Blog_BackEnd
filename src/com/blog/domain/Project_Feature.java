package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class Project_Feature extends Model<Project_Feature> {
    public static Project_Feature projectFeatureDao = new Project_Feature();

    public static List _toJSONList(List<Project_Feature> features) {
        List entry = new ArrayList();
        for (Project_Feature projectFeature : features) {
            entry.add(projectFeature.get("feature"));
        }
        return entry;
    }

}
