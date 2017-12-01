package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class Project_History extends Model<Project_History> {
    public static Project_History projectHistoryDao = new Project_History();

    public Map _toJson() {
        Map entry = new HashMap();
        entry.put("id", this.get("id"));
        entry.put("ver_id", this.get("ver_id"));
        entry.put("ver_date", this.get("ver_date"));
        return entry;
    }

    public static List<Map> _toJSONList(List<Project_History> histories) {
        List<Map> mapList = new ArrayList<>();
        for (Project_History history : histories) {
            mapList.add(history._toJson());
        }
        return mapList;
    }


}
