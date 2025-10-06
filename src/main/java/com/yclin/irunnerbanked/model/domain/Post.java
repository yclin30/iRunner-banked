package com.yclin.irunnerbanked.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 社区动态表
 * @TableName post
 */
@TableName(value ="post")
@Data
public class Post {
    /**
     * 动态ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发布者ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 关联的运动记录ID (可以为空)
     */
    @TableField(value = "activity_id")
    private Long activityId;

    /**
     * 动态文字内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 图片URL列表 (存储为JSON数组)
     */
    @TableField(value = "image_urls")
    private String imageUrls;

    /**
     * 发布时间
     */
    @TableField(value = "create_time")
    private Date createTime;
}