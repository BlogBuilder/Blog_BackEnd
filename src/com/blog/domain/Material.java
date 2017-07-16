package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class Material extends Model<Material> {
    public static Material materialDao = new Material();

    public Map _toJson() {
        Map entry = new HashMap();
        entry.put("id", this.get("id"));
        entry.put("material", this.getStr("material"));
        return entry;
    }

    public static List _toListJson(List<Material> materialList) {
        List arr = new ArrayList();
        for (Material material : materialList) {
            arr.add(material._toJson());
        }
        return arr;
    }
}
