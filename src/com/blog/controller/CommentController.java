package com.blog.controller;

import com.blog.domain.Article;
import com.blog.domain.Comment;
import com.blog.utils.ParaUtils;
import com.blog.utils.RenderUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.Render;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class CommentController extends Controller {
    /**
     * 创建评论
     */
    @Clear
    public void create() {
        try {
            int article_id = getParaToInt("id");
            Article article = Article.articleDao.findById(article_id);
            if (article != null) {
                Comment comment = new Comment();
                comment
                        .set("name", getRequest().getRemoteAddr())
                        .set("create_time", ParaUtils.timeFormat.format(new Date()))
                        .set("content", getPara("content"))
                        .set("article_id", getPara("id"))
                        .set("state", 1)
                        .set("parent", getParaToInt("parent"))
                        .set("photo", getParaToInt("photo"));
                Boolean result = comment.save();
                Map temp = RenderUtils.codeFactory(200);
                temp.put("comment", comment.get("id"));
                renderJson(result ? temp : RenderUtils.CODE_ERROR);
            }
        } catch (Exception e) {
            renderError(500);
        }
    }


    /**
     * 获取评论列表
     */
    @Clear
    public void list() {
        Integer id = getParaToInt("id");
        List<Record> recordList = Db.find("SELECT c.* FROM `db_comment` c WHERE c.parent IS NULL AND c.article_id =" + id);
        String json = "";
        for (int i = 0; i < recordList.size(); i++) {
            Record record = recordList.get(i);
            if (record != null) {
                json += findChild(record, id) + ",";
            }
        }
        if (recordList.size() > 0) {
            json = json.substring(0, json.length() - 1);
        }
        renderJson("{\"results\":[" + json + "]}");
    }


    public String findChild(Record parent, int blog_id) {
        List<Record> recordList = Db.find("SELECT c.* FROM `db_comment` c WHERE c.parent =" + parent.get("id") + " AND c.article_id =" + blog_id);
        String json = "";
        if (recordList.size() == 0) {
            //当前已经是最底层了,迭代结束,拼接JSON
            json = "{" +
                    "\"id\":" + parent.get("id") + "," +
                    "\"name\":\"" + ParaUtils.IPConvert(parent.getStr("name")) + "\"," +
                    "\"parent\":" + parent.get("parent") + "," +
                    "\"photo\":" + parent.get("photo") + "," +
                    "\"create_time\":\"" + parent.get("create_time") + "\"," +
                    "\"content\":\"" + parent.get("content") + "\"" +
                    "}";
            return json;
        } else {
            //往下层迭代
            String commentJSON = "";
            for (int i = 0; i < recordList.size(); i++) {
                Record comment = recordList.get(i);
                if (comment != null) {
                    commentJSON += findChild(comment, blog_id) + ",";
                }
            }
            commentJSON = commentJSON.substring(0, commentJSON.length() - 1);
            json = "{" +
                    "\"id\":" + parent.get("id") + "," +
                    "\"name\":\"" + ParaUtils.IPConvert(parent.getStr("name")) + "\"," +
                    "\"children\":[" + commentJSON + "]," +
                    "\"parent\":" + parent.get("parent") + "," +
                    "\"photo\":" + parent.get("photo") + "," +
                    "\"create_time\":\"" + parent.get("create_time") + "\"," +
                    "\"content\":\"" + parent.get("content") + "\"" +
                    "}";
        }
        return json;
    }


    @Clear
    public void recently() {
        try {
            List<Comment> commentList = Comment.toListJson(Comment.commentDao.find("SELECT * FROM `db_comment` c WHERE c.state=1 ORDER BY c.create_time DESC LIMIT 5"));
            Map result = new HashMap();
            result.put("results", commentList);
            renderJson(result);
        } catch (Exception e) {
            renderError(500);
        }
    }

    public void delete() {
        try {
            int id = getParaToInt("id");
            Boolean result = Comment.commentDao.deleteById(id);
            renderJson(result ? RenderUtils.CODE_SUCCESS : RenderUtils.CODE_ERROR);
        } catch (Exception e) {
            renderError(500);
        }
    }
}
