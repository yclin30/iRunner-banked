package com.yclin.irunnerbanked.model.request;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 发布新动态请求体
 */
@Data
public class PostAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 动态文字内容
     */
    private String content;

    /**
     * 关联的运动记录ID (可选)
     */
    private Long activityId;

    /**
     * 图片URL列表 (可选)
     */
    private List<String> imageUrls;
}