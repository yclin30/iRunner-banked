package com.yclin.irunnerbanked.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 账号
     */
    @TableField(value = "userAccount")
    private String useraccount;

    /**
     * 用户头像
     */
    @TableField(value = "avatarUrl")
    private String avatarurl;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 密码
     */
    @TableField(value = "userPassword")
    private String userpassword;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 状态 0-正常
     */
    @TableField(value = "userStatus")
    private Integer userstatus;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createtime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updatetime;

    /**
     * 是否删除
     */

    @TableLogic
    @TableField(value = "isDelete")
    private Integer isdelete;
}