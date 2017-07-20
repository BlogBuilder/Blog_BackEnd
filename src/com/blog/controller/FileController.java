
package com.blog.controller;


import com.blog.utils.QiniuUtil;
import com.blog.utils.RenderUtils;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import java.util.Map;


public class FileController extends Controller {
    /**
     * 文件上传功能
     */
    public void upload() {
        try {
            UploadFile uploadFile = getFile();
            String key = new QiniuUtil().upload(uploadFile.getFile());
            if (key != null) {
                Map result = RenderUtils.codeFactory(200);
                result.put("path", "http://cdn.qulongjun.cn/" + key);
                String[] data = {"http://cdn.qulongjun.cn/" + key};
                result.put("errno", 0);
                result.put("data", data);
                renderJson(result);
            } else
                renderJson(RenderUtils.CODE_ERROR);
        } catch (Exception e) {
            renderError(500);
        }
    }
}
