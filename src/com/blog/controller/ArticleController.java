package com.blog.controller;

import com.blog.domain.*;
import com.blog.utils.ParaUtils;
import com.blog.utils.RenderUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by qulongjun on 2017/7/15.
 */
public class ArticleController extends Controller {
    /**
     * 文章列表
     */
    @Clear
    public void list() {
        try {
            int rowCount = getParaToInt("rowCount");
            int currentPage = getParaToInt("currentPage");
            String condition_temp = getPara("condition");
            Map condition = ParaUtils.getSplitCondition(condition_temp);
            if (rowCount == 0) {
                rowCount = ParaUtils.getRowCount();
            }
            String paras = "WHERE 1=1 ";
            String tables = " FROM `db_article` a ";
            Object[] keys = condition.keySet().toArray();
            boolean flag = false;
            for (int i = 0; i < keys.length; i++) {
                String key = (String) keys[i];
                Object value = condition.get(key);
                switch (key) {
                    case "category":
                        tables += ",`db_article_category` c ";
                        paras += " AND c.article_id = a.id AND c.category_id=" + value + " ";
                        break;
                    case "tag":
                        tables += ",`db_article_tag` t ";
                        paras += " AND t.article_id=a.id AND t.tag_id=" + value + " ";
                        break;
                    case "key":
                        paras += " AND (a.title like \"%" + value + "%\" OR a.summary like \"%" + value + "%\") ";
                        break;
                    case "time":
                        paras += " AND date_format(a.create_time,'%Y-%m')='" + value + "' ";
                        break;
                    case "state":
                        flag = true;
                        paras += " AND a.state=" + value + " ";
                        break;
                    default:
                        paras += ("AND " + key + " = \"" + value + "\"");
                }
            }
            if (!flag) {
                paras += " AND a.state=1 ";
            }

            Page<Article> articlePage = Article.articleDao.paginate(currentPage, rowCount, "SELECT a.*", tables + paras);
            List<Article> articleList = articlePage.getList();
            Map results = new HashMap();
            results.put("results", Article._toListJson(articleList, true));
            results.put("currentPage", currentPage);
            results.put("totalPage", articlePage.getTotalPage());
            results.put("rowCount", rowCount);
            results.put("condition", condition_temp);
            renderJson(results);
        } catch (Exception e) {
            renderError(500);
        }
    }


    /**
     * 根据id查找文章
     */
    @Clear
    public void findById() {
        try {
            int article_id = getParaToInt("id");
            Article article = Article.articleDao.findFirst("SELECT * FROM `db_article` WHERE state=1 AND id=" + article_id);
            if (article != null) {
                article.set("view_count", article.getInt("view_count") + 1);
                Map result = article._toJson(false);
                renderJson(result);
            } else
                renderJson(RenderUtils.CODE_EMPTY);
        } catch (Exception e) {
            renderError(500);
        }
    }

    /**
     * 创建一篇博文
     */
    public void create() {
        try {
            Boolean result = Db.tx(new IAtom() {
                @Override
                public boolean run() throws SQLException {
                    Prop author = PropKit.use("author.properties");
                    Boolean result = true;
                    Article article = new Article();
                    article
                            .set("type", getPara("type"))
                            .set("title", getPara("title"))
                            .set("summary", getPara("summary"))
                            .set("create_time", ParaUtils.timeFormat.format(new Date()))
                            .set("author", author.get("name"))
                            .set("quote_author", getPara("quote_author"))
                            .set("content", getPara("content"))
                            .set("state", 1);
                    result = result && article.save();
                    Integer[] tagList = getParaValuesToInt("tags[]");
                    for (int tag : tagList) {
                        Article_Tag articleTag = new Article_Tag();
                        result = result && articleTag
                                .set("article_id", article.get("id"))
                                .set("tag_id", tag)
                                .save();
                    }
                    int category_id = getParaToInt("categoryId");
                    Article_Category articleCategory = new Article_Category();
                    result = result && articleCategory
                            .set("article_id", article.get("id"))
                            .set("category_id", category_id)
                            .save();

                    String[] materials = getParaValues("materials[]");
                    if (materials != null) {
                        for (String materialStr : materials) {
                            Material material = new Material();
                            result = result && material
                                    .set("article_id", article.get("id"))
                                    .set("material", materialStr)
                                    .save();
                        }
                    } else {
                        //使用默认素材
                        Prop setting = PropKit.use("setting.properties");
                        String defaultCover = "";
                        switch (getParaToInt("type")) {
                            case 1:
                            case 2:
                                defaultCover = setting.get("defaultCover");
                                break;
                            case 4:
                                defaultCover = setting.get("defaultAudio");
                                break;
                        }
                        if (defaultCover != "") {
                            Material material = new Material();
                            result = result && material
                                    .set("article_id", article.get("id"))
                                    .set("material", defaultCover)
                                    .save();
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
    public void hot() {
        try {
            List result = new ArrayList();
            List<Article> articleList = Article.articleDao.find("SELECT a.* FROM `db_comment` c,`db_article` a WHERE c.article_id=a.id AND a.type=2 AND a.state=1 GROUP BY c.article_id ORDER BY COUNT(*) DESC LIMIT 5\n");
            for (Article article : articleList) {
                Map temp = article._toJson(true);
                temp.put("comment_num", Comment.commentDao.find("SELECT * FROM `db_comment` c WHERE c.state=1 AND c.article_id=" + article.get("id")).size());
                result.add(temp);
            }
            Map t = new HashMap();
            t.put("results", result);
            renderJson(t);
        } catch (Exception e) {
            renderError(500);
        }

    }

    @Clear
    public void recently() {
        try {
            List<Article> articleList = Article._toListJson(Article.articleDao.find("SELECT * FROM `db_article` a WHERE a.state=1 AND a.type=2  ORDER BY create_time DESC LIMIT 0,5"), true);
            Map results = new HashMap();
            results.put("results", articleList);
            renderJson(results);
        } catch (Exception e) {
            renderError(500);
        }
    }


    /**
     * 将文章移动至回收站
     */
    public void dustbin() {
        try {
            int id = getParaToInt("id");
            Article article = Article.articleDao.findById(id);
            if (article != null) {
                Boolean result = article.set("state", -1).update();
                renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
            } else
                renderJson(RenderUtils.CODE_EMPTY);
        } catch (Exception e) {
            renderError(500);
        }
    }


    public void change() {
        try {
            Boolean result = Db.tx(new IAtom() {
                @Override
                public boolean run() throws SQLException {
                    int id = getParaToInt("id");
                    Article article = Article.articleDao.findById(id);
                    if (article != null) {
                        Boolean result = true;
                        article
                                .set("type", getPara("type"))
                                .set("title", getPara("title"))
                                .set("summary", getPara("summary"))
                                .set("quote_author", getPara("quote_author"))
                                .set("content", getPara("content"));
                        result = result && article.update();

                        List<Article_Tag> ids = Article_Tag.articleTagDao.find("SELECT id FROM `db_article_tag` WHERE article_id=" + id);
                        for (Article_Tag article_tag : ids) {
                            result = result && article_tag.delete();
                        }
                        Integer[] tagList = getParaValuesToInt("tags[]");
                        for (int tag : tagList) {
                            Article_Tag articleTag = new Article_Tag();
                            result = result && articleTag
                                    .set("article_id", article.get("id"))
                                    .set("tag_id", tag)
                                    .save();
                        }

                        Article_Category category = Article_Category.articleCategoryDao.findFirst("SELECT * FROM `db_article_category` WHERE article_id=" + id);
                        if (category != null) {
                            result = result && category.delete();
                        }
                        int category_id = getParaToInt("categoryId");
                        Article_Category articleCategory = new Article_Category();
                        result = result && articleCategory
                                .set("article_id", article.get("id"))
                                .set("category_id", category_id)
                                .save();


                        List<Material> materialIds = Material.materialDao.find("SELECT id FROM `db_article_material` WHERE article_id=" + id);
                        for (Material mat : materialIds) {
                            result = result && mat.delete();
                        }
                        String[] materials = getParaValues("materials[]");
                        for (String materialStr : materials) {
                            Material material = new Material();
                            result = result && material
                                    .set("article_id", article.get("id"))
                                    .set("material", materialStr)
                                    .save();
                        }


                        return result;
                    }
                    return false;
                }
            });
            renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
        } catch (Exception e) {
            renderError(500);
        }
    }
}
