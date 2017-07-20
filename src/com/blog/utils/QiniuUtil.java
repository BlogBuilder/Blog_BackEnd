package com.blog.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.File;
import java.io.IOException;

/**
 * Created by qulongjun on 2017/7/19.
 */
public class QiniuUtil {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "O3rDOs5bsuhGz3wMnFIXEIepomFD532jCEEhsScx"; //这两个登录七牛 账号里面可以找到
    String SECRET_KEY = "4nOVUHQg_OQeneEFTKCSg8Gs8ceSpIEjQ9iAO7LE";

    //要上传的空间
    String bucketname = "blog"; //对应要上传到七牛上 你的那个路径（自己建文件夹 注意设置公开）

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager();

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }


    //普通上传
    public String upload(File file) throws IOException {
        try {
            if (file == null) return null;
            String filename = file.getName();
            String randomFileName = RandomUtil.generateString(10) + filename.substring(filename.lastIndexOf("."));
            //上传到七牛后保存的文件名
            String key = "resource/" + randomFileName;
            //调用put方法上传
            Response res = uploadManager.put(file, key, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
            return key;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return null;
    }
}
