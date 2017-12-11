
package com.blog.controller;


import com.blog.utils.QiniuUtil;
import com.blog.utils.RenderUtils;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.sun.org.apache.regexp.internal.RE;

import java.io.File;
import java.util.Map;
import java.util.UUID;


public class FileController extends Controller {
    /**
     * 文件上传功能
     */
    @Clear
    public void upload() {
        try {
            UploadFile uploadFile = getFile();
            String newName = UUID.randomUUID().toString().replaceAll("-", "") + "." + uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf(".") + 1);
            System.out.println(newName);
            File f = uploadFile.getFile();
            File newFile = new File(PathKit.getWebRootPath() + "/upload/" + newName);
            f.renameTo(newFile);
            System.out.println(PathKit.getWebRootPath() + "/upload/" + newName);

            String key = new QiniuUtil().upload(newFile);

            Map result = RenderUtils.codeFactory(200);
//            result.put("path", "/upload/" + newName);
            result.put("path", "http://cdn.qulongjun.cn/" + key);
            String[] data = {"http://cdn.qulongjun.cn/" + key};
//            String[] data = {"/upload/" + newName};
            result.put("errno", 0);
            result.put("data", data);
            renderJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            renderError(500);
        }
    }
}
