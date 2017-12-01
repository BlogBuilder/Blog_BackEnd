package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class Project_Material extends Model<Project_Material> {
    public static Project_Material projectMaterialDao = new Project_Material();

    public static List _toJSONList(List<Project_Material> materials) {
        List entry = new ArrayList();
        for (Project_Material material : materials) {
            entry.add(material.get("material"));
        }
        return entry;
    }

}
