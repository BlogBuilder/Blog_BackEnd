package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class Project_Skill extends Model<Project_Skill> {
    public static Project_Skill projectSkillDao = new Project_Skill();

    public static List _toJSONList(List<Project_Skill> skills) {
        List entry = new ArrayList();
        for (Project_Skill projectSkill : skills) {
            entry.add(projectSkill.get("skill"));
        }
        return entry;
    }

}
