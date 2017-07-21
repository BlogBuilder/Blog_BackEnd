package com.blog.Interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 登录验证拦截器
 */
public class LoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        if (invocation.getController().getSession().getAttribute("loginState") != null && invocation.getController().getSession().getAttribute("loginState").equals("1")) {
            invocation.invoke();
        } else {
            invocation.getController().redirect("/login.html");
        }
    }
}
