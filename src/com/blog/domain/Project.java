package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/8/22.
 */
public class Project extends Model<Project> {
    public static Project projectDao = new Project();


    public Map _toJson() {
        Map result = new HashMap();//总的返回
        Map summary = new HashMap();//Summary
        result.put("id", this.get("id"));
        result.put("name", this.get("name"));
        result.put("info", this.get("info"));
        List<Project_Belongs_Category> project_belongs_categoryList = Project_Belongs_Category.projectBelongsCategoryDao.find("SELECT * FROM `db_project_belongs_category` WHERE project_id=" + this.get("id"));
        summary.put("category", Project_Belongs_Category._toJSONList(project_belongs_categoryList));
        List<Project_Participator> projectParticipators = Project_Participator.projectParticipatorDao.find("SELECT * FROM `db_project_participator` WHERE project_id=" + this.get("id"));
        summary.put("participator", Project_Participator._toJSONList(projectParticipators));
        summary.put("state", this.get("state"));
        summary.put("create_date", this.get("create_date"));
        summary.put("index_site", this.get("index_site"));
        result.put("summary", summary);
        List<Project_Feature> projectFeatures = Project_Feature.projectFeatureDao.find("SELECT * FROM `db_project_features` WHERE project_id=" + this.get("id"));
        result.put("features", Project_Feature._toJSONList(projectFeatures));
        List<Project_History> projectHistories = Project_History.projectHistoryDao.find("SELECT * FROM `db_project_history` WHERE project_id=" + this.get("id"));
        result.put("history", Project_History._toJSONList(projectHistories));
        List<Project_Material> projectMaterials = Project_Material.projectMaterialDao.find("SELECT * FROM `db_project_material` WHERE project_id=" + this.get("id"));
        result.put("materials", Project_Material._toJSONList(projectMaterials));
        List<Project_Skill> projectSkills = Project_Skill.projectSkillDao.find("SELECT * FROM `db_project_skills` WHERE project_id=" + this.get("id"));
        result.put("skills", Project_Skill._toJSONList(projectSkills));
        return result;
    }


    public static List<Map> _toJSONList(List<Project> projectList) {
        List<Map> result = new ArrayList<>();
        for (Project project : projectList) {
            result.add(project._toJson());
        }
        return result;
    }

}
