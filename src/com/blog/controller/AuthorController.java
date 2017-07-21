package com.blog.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class AuthorController extends Controller {
    @Clear
    public void info() {
        try {
            Prop p = PropKit.use("author.properties");
            Map author = new HashMap();
            author.put("name", p.get("name"));
            author.put("desp", p.get("desp"));
            author.put("photo", p.get("photo"));
            renderJson(author);
        } catch (Exception e) {
            renderError(500);
        }
    }
}
