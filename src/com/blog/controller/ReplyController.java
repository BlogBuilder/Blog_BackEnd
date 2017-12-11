package com.blog.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.junit.Test;

/**
 * Created by qulongjun on 2017/12/10.
 */
public class ReplyController {
    public void sample() {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIIhYM5yJCuvty", "MS6GCUYF3wsLwpExrNMtj1uGIdRMNN");
        // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理
        //try {
        //DefaultProfile.addEndpoint("dm.ap-southeast-1.aliyuncs.com", "ap-southeast-1", "Dm",  "dm.ap-southeast-1.aliyuncs.com");
        //} catch (ClientException e) {
        //e.printStackTrace();
        //}
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            request.setAccountName("noreply@qulongjun.com");
            request.setFromAlias("落苏");
            request.setAddressType(1);
            request.setTagName("Notice");
            request.setReplyToAddress(true);
            request.setToAddress("154256698@qq.com");
            request.setSubject("收到您的评论");
            request.setHtmlBody("<h1>这是邮件正文</h1>{EAddr}替换收件人邮箱地址； {UserName}替换收件人真实姓名； {NickName}替换收件人昵称； {Gender}替换收件人称呼（先生，女士）；{Birthday}替换收件人生日； {Mobile}替换收件人电话。");
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMail() {
        sample();
    }
}
