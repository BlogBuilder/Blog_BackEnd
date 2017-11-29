package com.blog.controller;

import com.blog.domain.*;
import com.blog.utils.RenderUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.json.Jackson;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/8/23.
 */
public class ProjectController extends Controller {

    @Clear
    public void create() {
        try {
            Boolean result = Db.tx(new IAtom() {
                @Override
                public boolean run() throws SQLException {
                    Boolean result = true;
                    Project project = new Project();
                    result = result && project
                            .set("name", getPara("name"))
                            .set("info", getPara("info"))
                            .set("process", getPara("process"))
                            .set("create_date", getPara("create_date"))
                            .set("index_site", getPara("index_site"))
                            .set("view_count", 0)
                            .set("state", 1)
                            .save();
                    if (!result) return false;
                    Integer[] categorys = getParaValuesToInt("categorys[]");
                    if (categorys.length != 0) {
                        for (int id : categorys) {
                            Project_Belongs_Category category = new Project_Belongs_Category();
                            result = result && category
                                    .set("project_id", project.get("id"))
                                    .set("category_id", id)
                                    .save();
                            if (!result) break;
                        }
                        if (!result) return false;
                    }
                    String[] features = getParaValues("features[]");
                    if (features.length != 0) {
                        for (String fe : features) {
                            Project_Feature feature = new Project_Feature();
                            result = result && feature
                                    .set("project_id", project.get("id"))
                                    .set("feature", fe)
                                    .save();
                            if (!result) return false;
                        }
                    }

                    String[] materials = getParaValues("materials[]");
                    if (materials.length != 0) {
                        for (String ma : materials) {
                            Project_Material material = new Project_Material();
                            result = result && material
                                    .set("project_id", project.get("id"))
                                    .set("material", ma)
                                    .save();
                            if (!result) return false;
                        }
                    }
                    String[] participators = getParaValues("participators[]");
                    if (participators.length != 0) {
                        for (String pa : participators) {
                            Project_Participator participator = new Project_Participator();
                            result = result && participator
                                    .set("project_id", project.get("id"))
                                    .set("participator", pa)
                                    .save();
                            if (!result) return false;
                        }
                    }

                    String[] skills = getParaValues("skills[]");
                    if (skills.length != 0) {
                        for (String sk : skills) {
                            Project_Skill skill = new Project_Skill();
                            result = result && skill
                                    .set("project_id", project.get("id"))
                                    .set("skill", sk)
                                    .save();
                            if (!result) return false;
                        }
                    }

                    String[] histories = getParaValues("histories[]");
                    if (histories.length != 0) {
                        for (String item : histories) {
                            Map temp = Jackson.getJson().parse(item, Map.class);
                            Project_History history = new Project_History();
                            result = result && history.set("project_id", project.get("id"))
                                    .set("ver_id", temp.get("ver_id"))
                                    .set("ver_date", temp.get("ver_date"))
                                    .save();
                            if (!result) return false;
                        }
                    }
                    return result;
                }
            });
            renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
        } catch (Exception e) {
            renderError(500);
        }
    }


    @Clear
    public void list() {
        try {
            List<Project> projectList = Project.projectDao.find("SELECT * FROM `db_project` WHERE state=1");
            renderJson(Project._toJSONList(projectList));
        } catch (Exception e) {
            renderError(500);
        }
    }
}
