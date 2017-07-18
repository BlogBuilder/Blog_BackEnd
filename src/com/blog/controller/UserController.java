package com.blog.controller;

import com.blog.utils.RenderUtils;
import com.jfinal.core.Controller;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * Created by qulongjun on 2017/7/18.
 */
public class UserController extends Controller {
    /**
     * 管理员登录
     */
    public void login() {
        try {
            Prop account = PropKit.use("account.properties");
            if (account.get("username").equals(getPara("username")) && account.get("password").equals(getPara("password"))) {
                getSession().setAttribute("loginState", "1");
                renderJson(RenderUtils.CODE_SUCCESS);
            } else {
                if (!account.get("username").equals(getPara("username"))) {
                    renderJson(RenderUtils.CODE_EMPTY);
                } else
                    renderJson(RenderUtils.CODE_ERROR);
            }
        } catch (Exception e) {
            renderError(500);
        }
    }
}
