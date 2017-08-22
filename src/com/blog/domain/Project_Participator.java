package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class Project_Participator extends Model<Project_Participator> {
    public static Project_Participator projectParticipatorDao = new Project_Participator();

    public static List _toJSONList(List<Project_Participator> participators) {
        List entry = new ArrayList();
        for (Project_Participator participator : participators) {
            entry.add(participator.get("participator"));
        }
        return entry;
    }

}
