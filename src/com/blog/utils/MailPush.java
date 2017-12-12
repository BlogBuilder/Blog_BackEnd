package com.blog.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;

import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.blog.domain.Article;
import com.blog.domain.Subscribe;

/**
 * Created by qulongjun on 2017/12/11.
 */
public class MailPush {

    public static void sendCommentEmail(int article_id, String article_title, String comment_user, String comment_time, String comment_content, Subscribe subscribe) {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIIhYM5yJCuvty", "MS6GCUYF3wsLwpExrNMtj1uGIdRMNN");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName("noreply@qulongjun.com");
            request.setFromAlias("落苏");
            request.setAddressType(1);
            request.setReplyToAddress(true);
            request.setToAddress(subscribe.getStr("email"));
            request.setSubject("您关注的文章有新动态啦！");
            request.setHtmlBody(getTemplate(article_id, article_title, comment_user, comment_time, comment_content, subscribe.getStr("cancel_id")));
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            System.out.println(httpResponse);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送文章关注邮件
     *
     * @param article
     * @param subscribe
     */
    public static boolean sendActivateEmail(Article article, Subscribe subscribe) {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIIhYM5yJCuvty", "MS6GCUYF3wsLwpExrNMtj1uGIdRMNN");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName("noreply@qulongjun.com");
            request.setFromAlias("落苏");
            request.setAddressType(1);
            request.setReplyToAddress(true);
            request.setToAddress(subscribe.getStr("email"));
            request.setSubject("落苏-确认关注文章动态");
            request.setHtmlBody(getActivateTemplate(article, subscribe.getStr("activation")));
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            System.out.println(httpResponse);
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getActivateTemplate(Article article, String activeCode) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title></title>\n" +
                "\t<style type=\"text/css\">\n" +
                "\t\t@charset \"utf-8\";\n" +
                "\n" +
                "/* 防止用户自定义背景颜色对网页的影响，添加让用户可以自定义字体 */\n" +
                "html {\n" +
                "  color: #333;\n" +
                "  background: #fff;\n" +
                "  -webkit-text-size-adjust: 100%;\n" +
                "  -ms-text-size-adjust: 100%;\n" +
                "  text-rendering: optimizelegibility;\n" +
                "}\n" +
                "\n" +
                "/* 如果你的项目仅支持 IE9+ | Chrome | Firefox 等，推荐在 <html> 中添加 .borderbox 这个 class */\n" +
                "html.borderbox *, html.borderbox *:before, html.borderbox *:after {\n" +
                "  -moz-box-sizing: border-box;\n" +
                "  -webkit-box-sizing: border-box;\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "/* 内外边距通常让各个浏览器样式的表现位置不同 */\n" +
                "body, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, textarea, p, blockquote, th, td, hr, button, article, aside, details, figcaption, figure, footer, header, menu, nav, section {\n" +
                "  margin: 0;\n" +
                "  padding: 0;\n" +
                "}\n" +
                "\n" +
                "/* 重设 HTML5 标签, IE 需要在 js 中 createElement(TAG) */\n" +
                "article, aside, details, figcaption, figure, footer, header, menu, nav, section {\n" +
                "  display: block;\n" +
                "}\n" +
                "\n" +
                "/* HTML5 媒体文件跟 img 保持一致 */\n" +
                "audio, canvas, video {\n" +
                "  display: inline-block;\n" +
                "}\n" +
                "\n" +
                "/* 要注意表单元素并不继承父级 font 的问题 */\n" +
                "body, button, input, select, textarea {\n" +
                "  font: 300 1em/1.8 PingFang SC, Lantinghei SC, Microsoft Yahei, Hiragino Sans GB, Microsoft Sans Serif, WenQuanYi Micro Hei, sans-serif;\n" +
                "}\n" +
                "\n" +
                "button::-moz-focus-inner,\n" +
                "input::-moz-focus-inner {\n" +
                "  padding: 0;\n" +
                "  border: 0;\n" +
                "}\n" +
                "\n" +
                "/* 去掉各Table cell 的边距并让其边重合 */\n" +
                "table {\n" +
                "  border-collapse: collapse;\n" +
                "  border-spacing: 0;\n" +
                "}\n" +
                "\n" +
                "/* 去除默认边框 */\n" +
                "fieldset, img {\n" +
                "  border: 0;\n" +
                "}\n" +
                "\n" +
                "/* 块/段落引用 */\n" +
                "blockquote {\n" +
                "  position: relative;\n" +
                "  color: #999;\n" +
                "  font-weight: 400;\n" +
                "  border-left: 1px solid #1abc9c;\n" +
                "  padding-left: 1em;\n" +
                "  margin: 1em 3em 1em 2em;\n" +
                "}\n" +
                "\n" +
                "@media only screen and ( max-width: 640px ) {\n" +
                "  blockquote {\n" +
                "    margin: 1em 0;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "/* Firefox 以外，元素没有下划线，需添加 */\n" +
                "acronym, abbr {\n" +
                "  border-bottom: 1px dotted;\n" +
                "  font-variant: normal;\n" +
                "}\n" +
                "\n" +
                "/* 添加鼠标问号，进一步确保应用的语义是正确的（要知道，交互他们也有洁癖，如果你不去掉，那得多花点口舌） */\n" +
                "abbr {\n" +
                "  cursor: help;\n" +
                "}\n" +
                "\n" +
                "/* 一致的 del 样式 */\n" +
                "del {\n" +
                "  text-decoration: line-through;\n" +
                "}\n" +
                "\n" +
                "address, caption, cite, code, dfn, em, th, var {\n" +
                "  font-style: normal;\n" +
                "  font-weight: 400;\n" +
                "}\n" +
                "\n" +
                "/* 去掉列表前的标识, li 会继承，大部分网站通常用列表来很多内容，所以应该当去 */\n" +
                "ul, ol {\n" +
                "  list-style: none;\n" +
                "}\n" +
                "\n" +
                "/* 对齐是排版最重要的因素, 别让什么都居中 */\n" +
                "caption, th {\n" +
                "  text-align: left;\n" +
                "}\n" +
                "\n" +
                "q:before, q:after {\n" +
                "  content: '';\n" +
                "}\n" +
                "\n" +
                "/* 统一上标和下标 */\n" +
                "sub, sup {\n" +
                "  font-size: 75%;\n" +
                "  line-height: 0;\n" +
                "  position: relative;\n" +
                "}\n" +
                "\n" +
                ":root sub, :root sup {\n" +
                "  vertical-align: baseline; /* for ie9 and other modern browsers */\n" +
                "}\n" +
                "\n" +
                "sup {\n" +
                "  top: -0.5em;\n" +
                "}\n" +
                "\n" +
                "sub {\n" +
                "  bottom: -0.25em;\n" +
                "}\n" +
                "\n" +
                "/* 让链接在 hover 状态下显示下划线 */\n" +
                "a {\n" +
                "  color: #1abc9c;\n" +
                "}\n" +
                "\n" +
                "a:hover {\n" +
                "  text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                ".typo a {\n" +
                "}\n" +
                "\n" +
                ".typo a:hover {\n" +
                "  border-bottom-color: #555;\n" +
                "  color: #555;\n" +
                "  text-decoration: none;\n" +
                "}\n" +
                "\n" +
                "/* 默认不显示下划线，保持页面简洁 */\n" +
                "ins, a {\n" +
                "  text-decoration: none;\n" +
                "}\n" +
                "\n" +
                "/* 专名号：虽然 u 已经重回 html5 Draft，但在所有浏览器中都是可以使用的，\n" +
                " * 要做到更好，向后兼容的话，添加 class=\"typo-u\" 来显示专名号\n" +
                " * 关于 <u> 标签：http://www.whatwg.org/specs/web-apps/current-work/multipage/text-level-semantics.html#the-u-element\n" +
                " * 被放弃的是 4，之前一直搞错 http://www.w3.org/TR/html401/appendix/changes.html#idx-deprecated\n" +
                " * 一篇关于 <u> 标签的很好文章：http://html5doctor.com/u-element/\n" +
                " */\n" +
                "u, .typo-u {\n" +
                "  text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                "/* 标记，类似于手写的荧光笔的作用 */\n" +
                "mark {\n" +
                "  background: #fffdd1;\n" +
                "  border-bottom: 1px solid #ffedce;\n" +
                "  padding: 2px;\n" +
                "  margin: 0 5px;\n" +
                "}\n" +
                "\n" +
                "/* 代码片断 */\n" +
                "pre, code, pre tt {\n" +
                "  font-family: Courier, 'Courier New', monospace;\n" +
                "}\n" +
                "\n" +
                "pre {\n" +
                "  background: #f8f8f8;\n" +
                "  border: 1px solid #ddd;\n" +
                "  padding: 1em 1.5em;\n" +
                "  display: block;\n" +
                "  -webkit-overflow-scrolling: touch;\n" +
                "}\n" +
                "\n" +
                "/* 一致化 horizontal rule */\n" +
                "hr {\n" +
                "  border: none;\n" +
                "  border-bottom: 1px solid #cfcfcf;\n" +
                "  margin-bottom: 0.8em;\n" +
                "  height: 10px;\n" +
                "}\n" +
                "\n" +
                "/* 底部印刷体、版本等标记 */\n" +
                "small, .typo-small,\n" +
                "  /* 图片说明 */\n" +
                "figcaption {\n" +
                "  font-size: 0.9em;\n" +
                "  color: #888;\n" +
                "}\n" +
                "\n" +
                "strong, b {\n" +
                "  font-weight: bold;\n" +
                "  color: #000;\n" +
                "}\n" +
                "\n" +
                "/* 可拖动文件添加拖动手势 */\n" +
                "[draggable] {\n" +
                "  cursor: move;\n" +
                "}\n" +
                "\n" +
                ".clearfix:before, .clearfix:after {\n" +
                "  content: \"\";\n" +
                "  display: table;\n" +
                "}\n" +
                "\n" +
                ".clearfix:after {\n" +
                "  clear: both;\n" +
                "}\n" +
                "\n" +
                ".clearfix {\n" +
                "  zoom: 1;\n" +
                "}\n" +
                "\n" +
                "/* 强制文本换行 */\n" +
                ".textwrap, .textwrap td, .textwrap th {\n" +
                "  word-wrap: break-word;\n" +
                "  word-break: break-all;\n" +
                "}\n" +
                "\n" +
                ".textwrap-table {\n" +
                "  table-layout: fixed;\n" +
                "}\n" +
                "\n" +
                "/* 提供 serif 版本的字体设置: iOS 下中文自动 fallback 到 sans-serif */\n" +
                ".serif {\n" +
                "  font-family: Palatino, Optima, Georgia, serif;\n" +
                "}\n" +
                "\n" +
                "/* 保证块/段落之间的空白隔行 */\n" +
                ".typo p, .typo pre, .typo ul, .typo ol, .typo dl, .typo form, .typo hr, .typo table,\n" +
                ".typo-p, .typo-pre, .typo-ul, .typo-ol, .typo-dl, .typo-form, .typo-hr, .typo-table, blockquote {\n" +
                "  margin-bottom: 0.8em\n" +
                "}\n" +
                "\n" +
                "h1, h2, h3, h4, h5, h6 {\n" +
                "  font-family: PingFang SC, Verdana, Helvetica Neue, Microsoft Yahei, Hiragino Sans GB, Microsoft Sans Serif, WenQuanYi Micro Hei, sans-serif;\n" +
                "  font-weight: 100;\n" +
                "  color: #000;\n" +
                "  line-height: 1.35;\n" +
                "}\n" +
                "\n" +
                "/* 标题应该更贴紧内容，并与其他块区分，margin 值要相应做优化 */\n" +
                ".typo h1, .typo h2, .typo h3, .typo h4, .typo h5, .typo h6,\n" +
                ".typo-h1, .typo-h2, .typo-h3, .typo-h4, .typo-h5, .typo-h6 {\n" +
                "  margin-top: 0.8em;\n" +
                "  margin-bottom: 0.6em;\n" +
                "  line-height: 1.35;\n" +
                "}\n" +
                "\n" +
                ".typo h1, .typo-h1 {\n" +
                "  font-size: 2em;\n" +
                "}\n" +
                "\n" +
                ".typo h2, .typo-h2 {\n" +
                "  font-size: 1.8em;\n" +
                "}\n" +
                "\n" +
                ".typo h3, .typo-h3 {\n" +
                "  font-size: 1.6em;\n" +
                "}\n" +
                "\n" +
                ".typo h4, .typo-h4 {\n" +
                "  font-size: 1.4em;\n" +
                "}\n" +
                "\n" +
                ".typo h5, .typo h6, .typo-h5, .typo-h6 {\n" +
                "  font-size: 1.2em;\n" +
                "}\n" +
                "\n" +
                "/* 同 ul/ol，在文章中应用 table 基本格式 */\n" +
                ".typo table th, .typo table td, .typo-table th, .typo-table td, .typo table caption {\n" +
                "  border: 1px solid #ddd;\n" +
                "  padding: 0.5em 1em;\n" +
                "  color: #666;\n" +
                "}\n" +
                "\n" +
                ".typo table th, .typo-table th {\n" +
                "  background: #fbfbfb;\n" +
                "}\n" +
                "\n" +
                ".typo table thead th, .typo-table thead th {\n" +
                "  background: #f1f1f1;\n" +
                "}\n" +
                "\n" +
                ".typo table caption {\n" +
                "  border-bottom: none;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div class=\"typo\" style=\"width:60%;margin:0 auto; margin-bottom:50px;margin-top:50px;\">\n" +
                "\t<p>尊敬的读者{EAddr}：</p>\n" +
                "\t<p style=\"text-indent:2em;\">您好！</p>\n" +
                "\t<p style=\"text-indent:2em;\">您在<a href=\"https://qulongjun.com\">落苏</a>上关注了文章<a href=\"https://qulongjun.com/article/" + article.get("id") + "\">《" + article.get("title") + "》</a>，我们将在第一时间通知您文章动态，以下是文章简介：</p>\n" +
                "\t<div style=\"text-align:center;margin-top:50px\">\n" +
                "  <div style=\"width:80%;margin:0 auto;\">\n" +
                "    <table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "     <td><p>文章标题</p></td>\n" +
                "     <td><p>《" + article.get("title") + "》</p></td>\n" +
                "   </tr>\n" +
                "   <tr>\n" +
                "     <td><p>发布时间</p></td>\n" +
                "     <td><p>" + article.get("create_time") + "</p></td>\n" +
                "   </tr>\n" +
                "    <tr>\n" +
                "     <td><p>文章摘要</p></td>\n" +
                "     <td>\n" +
                "    <p style=\"text-align:left\">" + article.get("summary") + "</p>\n" +
                "     </td>\n" +
                "   </tr>\n" +
                "  </table>\n" +
                "  </div>\n" +
                "  <p style=\"text-align:left;margin-top:50px;text-indent:2em;\">点击<a href=\"http://api.qulongjun.cn/api/subscribe/activate?activate_id=" + activeCode + "\">此处</a>确认关注该文章动态，若点击按钮无效，复制以下链接到浏览器访问：</p>\n" +
                "  <p style=\"text-align:left;margin-top:10px;text-indent:2em;\">\n" +
                "    <a href=\"http://api.qulongjun.cn/api/subscribe/activate?activate_id=" + activeCode + "\">http://api.qulongjun.cn/api/subscribe/activate?activate_id=" + activeCode + "</a>\n" +
                "  </p>\n" +
                "\t\n" +
                "\t\n" +
                "\t<div style=\"text-align:left;margin-top:100px;\">\n" +
                "        <hr>\n" +
                "      <p style=\"text-indent:2em;\">此邮件为自动发送，请勿回复。</p>\n" +
                "\t</div>\n" +
                "\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>";
    }


    public static String getTemplate(int article_id, String article_title, String comment_user, String comment_time, String comment_content, String cancel_id) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title></title>\n" +
                "\t<style type=\"text/css\">\n" +
                "\t\t@charset \"utf-8\";\n" +
                "\n" +
                "/* 防止用户自定义背景颜色对网页的影响，添加让用户可以自定义字体 */\n" +
                "html {\n" +
                "  color: #333;\n" +
                "  background: #fff;\n" +
                "  -webkit-text-size-adjust: 100%;\n" +
                "  -ms-text-size-adjust: 100%;\n" +
                "  text-rendering: optimizelegibility;\n" +
                "}\n" +
                "\n" +
                "/* 如果你的项目仅支持 IE9+ | Chrome | Firefox 等，推荐在 <html> 中添加 .borderbox 这个 class */\n" +
                "html.borderbox *, html.borderbox *:before, html.borderbox *:after {\n" +
                "  -moz-box-sizing: border-box;\n" +
                "  -webkit-box-sizing: border-box;\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "/* 内外边距通常让各个浏览器样式的表现位置不同 */\n" +
                "body, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, textarea, p, blockquote, th, td, hr, button, article, aside, details, figcaption, figure, footer, header, menu, nav, section {\n" +
                "  margin: 0;\n" +
                "  padding: 0;\n" +
                "}\n" +
                "\n" +
                "/* 重设 HTML5 标签, IE 需要在 js 中 createElement(TAG) */\n" +
                "article, aside, details, figcaption, figure, footer, header, menu, nav, section {\n" +
                "  display: block;\n" +
                "}\n" +
                "\n" +
                "/* HTML5 媒体文件跟 img 保持一致 */\n" +
                "audio, canvas, video {\n" +
                "  display: inline-block;\n" +
                "}\n" +
                "\n" +
                "/* 要注意表单元素并不继承父级 font 的问题 */\n" +
                "body, button, input, select, textarea {\n" +
                "  font: 300 1em/1.8 PingFang SC, Lantinghei SC, Microsoft Yahei, Hiragino Sans GB, Microsoft Sans Serif, WenQuanYi Micro Hei, sans-serif;\n" +
                "}\n" +
                "\n" +
                "button::-moz-focus-inner,\n" +
                "input::-moz-focus-inner {\n" +
                "  padding: 0;\n" +
                "  border: 0;\n" +
                "}\n" +
                "\n" +
                "/* 去掉各Table cell 的边距并让其边重合 */\n" +
                "table {\n" +
                "  border-collapse: collapse;\n" +
                "  border-spacing: 0;\n" +
                "}\n" +
                "\n" +
                "/* 去除默认边框 */\n" +
                "fieldset, img {\n" +
                "  border: 0;\n" +
                "}\n" +
                "\n" +
                "/* 块/段落引用 */\n" +
                "blockquote {\n" +
                "  position: relative;\n" +
                "  color: #999;\n" +
                "  font-weight: 400;\n" +
                "  border-left: 1px solid #1abc9c;\n" +
                "  padding-left: 1em;\n" +
                "  margin: 1em 3em 1em 2em;\n" +
                "}\n" +
                "\n" +
                "@media only screen and ( max-width: 640px ) {\n" +
                "  blockquote {\n" +
                "    margin: 1em 0;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "/* Firefox 以外，元素没有下划线，需添加 */\n" +
                "acronym, abbr {\n" +
                "  border-bottom: 1px dotted;\n" +
                "  font-variant: normal;\n" +
                "}\n" +
                "\n" +
                "/* 添加鼠标问号，进一步确保应用的语义是正确的（要知道，交互他们也有洁癖，如果你不去掉，那得多花点口舌） */\n" +
                "abbr {\n" +
                "  cursor: help;\n" +
                "}\n" +
                "\n" +
                "/* 一致的 del 样式 */\n" +
                "del {\n" +
                "  text-decoration: line-through;\n" +
                "}\n" +
                "\n" +
                "address, caption, cite, code, dfn, em, th, var {\n" +
                "  font-style: normal;\n" +
                "  font-weight: 400;\n" +
                "}\n" +
                "\n" +
                "/* 去掉列表前的标识, li 会继承，大部分网站通常用列表来很多内容，所以应该当去 */\n" +
                "ul, ol {\n" +
                "  list-style: none;\n" +
                "}\n" +
                "\n" +
                "/* 对齐是排版最重要的因素, 别让什么都居中 */\n" +
                "caption, th {\n" +
                "  text-align: left;\n" +
                "}\n" +
                "\n" +
                "q:before, q:after {\n" +
                "  content: '';\n" +
                "}\n" +
                "\n" +
                "/* 统一上标和下标 */\n" +
                "sub, sup {\n" +
                "  font-size: 75%;\n" +
                "  line-height: 0;\n" +
                "  position: relative;\n" +
                "}\n" +
                "\n" +
                ":root sub, :root sup {\n" +
                "  vertical-align: baseline; /* for ie9 and other modern browsers */\n" +
                "}\n" +
                "\n" +
                "sup {\n" +
                "  top: -0.5em;\n" +
                "}\n" +
                "\n" +
                "sub {\n" +
                "  bottom: -0.25em;\n" +
                "}\n" +
                "\n" +
                "/* 让链接在 hover 状态下显示下划线 */\n" +
                "a {\n" +
                "  color: #1abc9c;\n" +
                "}\n" +
                "\n" +
                "a:hover {\n" +
                "  text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                ".typo a {\n" +
                "  \n" +
                "}\n" +
                "\n" +
                ".typo a:hover {\n" +
                "  border-bottom-color: #555;\n" +
                "  color: #555;\n" +
                "  text-decoration: none;\n" +
                "}\n" +
                "\n" +
                "/* 默认不显示下划线，保持页面简洁 */\n" +
                "ins, a {\n" +
                "  text-decoration: none;\n" +
                "}\n" +
                "\n" +
                "/* 专名号：虽然 u 已经重回 html5 Draft，但在所有浏览器中都是可以使用的，\n" +
                " * 要做到更好，向后兼容的话，添加 class=\"typo-u\" 来显示专名号\n" +
                " * 关于 <u> 标签：http://www.whatwg.org/specs/web-apps/current-work/multipage/text-level-semantics.html#the-u-element\n" +
                " * 被放弃的是 4，之前一直搞错 http://www.w3.org/TR/html401/appendix/changes.html#idx-deprecated\n" +
                " * 一篇关于 <u> 标签的很好文章：http://html5doctor.com/u-element/\n" +
                " */\n" +
                "u, .typo-u {\n" +
                "  text-decoration: underline;\n" +
                "}\n" +
                "\n" +
                "/* 标记，类似于手写的荧光笔的作用 */\n" +
                "mark {\n" +
                "  background: #fffdd1;\n" +
                "  border-bottom: 1px solid #ffedce;\n" +
                "  padding: 2px;\n" +
                "  margin: 0 5px;\n" +
                "}\n" +
                "\n" +
                "/* 代码片断 */\n" +
                "pre, code, pre tt {\n" +
                "  font-family: Courier, 'Courier New', monospace;\n" +
                "}\n" +
                "\n" +
                "pre {\n" +
                "  background: #f8f8f8;\n" +
                "  border: 1px solid #ddd;\n" +
                "  padding: 1em 1.5em;\n" +
                "  display: block;\n" +
                "  -webkit-overflow-scrolling: touch;\n" +
                "}\n" +
                "\n" +
                "/* 一致化 horizontal rule */\n" +
                "hr {\n" +
                "  border: none;\n" +
                "  border-bottom: 1px solid #cfcfcf;\n" +
                "  margin-bottom: 0.8em;\n" +
                "  height: 10px;\n" +
                "}\n" +
                "\n" +
                "/* 底部印刷体、版本等标记 */\n" +
                "small, .typo-small,\n" +
                "  /* 图片说明 */\n" +
                "figcaption {\n" +
                "  font-size: 0.9em;\n" +
                "  color: #888;\n" +
                "}\n" +
                "\n" +
                "strong, b {\n" +
                "  font-weight: bold;\n" +
                "  color: #000;\n" +
                "}\n" +
                "\n" +
                "/* 可拖动文件添加拖动手势 */\n" +
                "[draggable] {\n" +
                "  cursor: move;\n" +
                "}\n" +
                "\n" +
                ".clearfix:before, .clearfix:after {\n" +
                "  content: \"\";\n" +
                "  display: table;\n" +
                "}\n" +
                "\n" +
                ".clearfix:after {\n" +
                "  clear: both;\n" +
                "}\n" +
                "\n" +
                ".clearfix {\n" +
                "  zoom: 1;\n" +
                "}\n" +
                "\n" +
                "/* 强制文本换行 */\n" +
                ".textwrap, .textwrap td, .textwrap th {\n" +
                "  word-wrap: break-word;\n" +
                "  word-break: break-all;\n" +
                "}\n" +
                "\n" +
                ".textwrap-table {\n" +
                "  table-layout: fixed;\n" +
                "}\n" +
                "\n" +
                "/* 提供 serif 版本的字体设置: iOS 下中文自动 fallback 到 sans-serif */\n" +
                ".serif {\n" +
                "  font-family: Palatino, Optima, Georgia, serif;\n" +
                "}\n" +
                "\n" +
                "/* 保证块/段落之间的空白隔行 */\n" +
                ".typo p, .typo pre, .typo ul, .typo ol, .typo dl, .typo form, .typo hr, .typo table,\n" +
                ".typo-p, .typo-pre, .typo-ul, .typo-ol, .typo-dl, .typo-form, .typo-hr, .typo-table, blockquote {\n" +
                "  margin-bottom: 0.8em\n" +
                "}\n" +
                "\n" +
                "h1, h2, h3, h4, h5, h6 {\n" +
                "  font-family: PingFang SC, Verdana, Helvetica Neue, Microsoft Yahei, Hiragino Sans GB, Microsoft Sans Serif, WenQuanYi Micro Hei, sans-serif;\n" +
                "  font-weight: 100;\n" +
                "  color: #000;\n" +
                "  line-height: 1.35;\n" +
                "}\n" +
                "\n" +
                "/* 标题应该更贴紧内容，并与其他块区分，margin 值要相应做优化 */\n" +
                ".typo h1, .typo h2, .typo h3, .typo h4, .typo h5, .typo h6,\n" +
                ".typo-h1, .typo-h2, .typo-h3, .typo-h4, .typo-h5, .typo-h6 {\n" +
                "  margin-top: 0.8em;\n" +
                "  margin-bottom: 0.6em;\n" +
                "  line-height: 1.35;\n" +
                "}\n" +
                "\n" +
                ".typo h1, .typo-h1 {\n" +
                "  font-size: 2em;\n" +
                "}\n" +
                "\n" +
                ".typo h2, .typo-h2 {\n" +
                "  font-size: 1.8em;\n" +
                "}\n" +
                "\n" +
                ".typo h3, .typo-h3 {\n" +
                "  font-size: 1.6em;\n" +
                "}\n" +
                "\n" +
                ".typo h4, .typo-h4 {\n" +
                "  font-size: 1.4em;\n" +
                "}\n" +
                "\n" +
                ".typo h5, .typo h6, .typo-h5, .typo-h6 {\n" +
                "  font-size: 1.2em;\n" +
                "}\n" +
                "\n" +
                "/* 同 ul/ol，在文章中应用 table 基本格式 */\n" +
                ".typo table th, .typo table td, .typo-table th, .typo-table td, .typo table caption {\n" +
                "  border: 1px solid #ddd;\n" +
                "  padding: 0.5em 1em;\n" +
                "  color: #666;\n" +
                "}\n" +
                "\n" +
                ".typo table th, .typo-table th {\n" +
                "  background: #fbfbfb;\n" +
                "}\n" +
                "\n" +
                ".typo table thead th, .typo-table thead th {\n" +
                "  background: #f1f1f1;\n" +
                "}\n" +
                "\n" +
                ".typo table caption {\n" +
                "  border-bottom: none;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div class=\"typo\" style=\"width:60%;margin:0 auto; margin-bottom:50px;margin-top:50px;\">\n" +
                "\t<p>尊敬的读者{EAddr}：</p>\n" +
                "\t<p style=\"text-indent:2em;\">您好！</p>\n" +
                "\t<p style=\"text-indent:2em;\">您在<a href=\"https://qulongjun.com\">落苏</a>上关注的文章<a href=\"https://qulongjun.com/article/" + article_id + "\">《" + article_title + "》</a>有了新的评论动态，以下是评论详情：</p>\n" +
                "\t<div style=\"text-align:center;margin-top:50px\">\n" +
                "  <div style=\"width:80%;margin:0 auto;\">\n" +
                "    <table style=\"width:100%\">\n" +
                "   <tr>\n" +
                "     <td style=\"width:20%\">评论者</td>\n" +
                "     <td>" + comment_user + "</td>\n" +
                "   </tr>\n" +
                "    <tr>\n" +
                "     <td>评论时间</td>\n" +
                "     <td> " + comment_time + "</td>\n" +
                "   </tr>\n" +
                "    <tr>\n" +
                "     <td>相关文章</td>\n" +
                "     <td><a href=\"https://qulongjun.com/article/" + article_id + "\">《" + article_title + "》</a></td>\n" +
                "   </tr>\n" +
                "    <tr>\n" +
                "     <td>评论内容</td>\n" +
                "     <td>\n" +
                "       <blockquote style=\"text-align:left;\">\n" +
                "    " + comment_content + "\n" +
                "  </blockquote>\n" +
                "     </td>\n" +
                "   </tr>\n" +
                "  </table>\n" +
                "  </div>\n" +
                "  <p style=\"text-align:left;margin-top:30px;text-indent:2em;\">您可以<a href=\"https://qulongjun.com/article/" + article_id + "\">点此</a>查看文章详情，或<a href=\"https://qulongjun.com\">查看</a>更多感兴趣的文章...</p>\n" +
                "\t\n" +
                "\t\n" +
                "\t<div style=\"text-align:left;margin-top:100px;\">\n" +
                "        <hr>\n" +
                "\t\t\t<p style=\"text-indent:2em;\">您收到此邮件是因为您关注了该文章的更新，若您对此类邮件感到困惑，您可以<a href=\"http://api.qulongjun.cn/api/subscribe/cancel?cancel_id=" + cancel_id + "\" >点此</a>退订。</p>\n" +
                "      <p style=\"text-indent:2em;\">此邮件为自动发送，请勿回复。</p>\n" +
                "\t</div>\n" +
                "\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>";
    }

}
