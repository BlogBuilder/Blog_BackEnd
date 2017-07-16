package com.blog.domain;

import com.jfinal.plugin.activerecord.Model;

/**
 * Created by qulongjun on 2017/7/16.
 */
public class Comment extends Model<Comment> {
    public static Comment commentDao = new Comment();

}
