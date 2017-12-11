package com.blog.config;

import com.blog.Interceptor.LoginInterceptor;
import com.blog.domain.*;
import com.jfinal.config.*;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * JFinal通用配置文件
 */
public class CommonConfig extends JFinalConfig {
    /**
     * 配置JFInal的常量
     *
     * @param me
     */
    @Override
    public void configConstant(Constants me) {
        //读取配置文件
        loadPropertyFile("jdbc.properties");
        //设置开发模式,如果设置为true,控制台会输出每次请求的Controller action和参数信息
        me.setDevMode(getPropertyToBoolean("devMode", false));
        //设置视图模型
        me.setViewType(ViewType.JSP);
        me.setBaseViewPath("/WEB-INF/pages");
        //me.setError404View("404.html");
        //设置下载路径
        // me.setBaseDownloadPath("/WEB-INF/download");
        //设置上传路径
        me.setBaseUploadPath(PathKit.getWebRootPath() + "/upload");
        //http://localhost:8080/blog/1-3
        me.setUrlParaSeparator("-");
        me.setError403View("/WEB-INF/pages/common/404.html");
    }

    @Override
    public void configRoute(Routes me) {
        me.add(new FrontRoutes());
    }

    @Override
    public void configPlugin(Plugins me) {
        Prop p = PropKit.use("jdbc.properties");
        C3p0Plugin cp = new C3p0Plugin(p.get("jdbcDriver"), p.get("username"), p.get("password"));
        me.add(cp);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        me.add(arp);
        arp.addMapping("db_article", Article.class);
        arp.addMapping("db_tag", Tag.class);
        arp.addMapping("db_category", Category.class);
        arp.addMapping("db_article_material", Material.class);
        arp.addMapping("db_article_tag", Article_Tag.class);
        arp.addMapping("db_article_category", Article_Category.class);
        arp.addMapping("db_comment", Comment.class);
        arp.addMapping("db_article_subscribe", Subscribe.class);

        arp.addMapping("db_project", Project.class);
        arp.addMapping("db_project_category", Project_Category.class);
        arp.addMapping("db_project_belongs_category", Project_Belongs_Category.class);
        arp.addMapping("db_project_features", Project_Feature.class);
        arp.addMapping("db_project_history", Project_History.class);
        arp.addMapping("db_project_material", Project_Material.class);
        arp.addMapping("db_project_participator", Project_Participator.class);
        arp.addMapping("db_project_skills", Project_Skill.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new LoginInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {

    }
}
