package com.yclin.irunnerbanked.model.vo;

import com.yclin.irunnerbanked.model.domain.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 动态视图对象 (包含了作者信息)
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostVO extends Post {

    private static final long serialVersionUID = 1L;

    /**
     * 作者昵称
     */
    private String authorUsername;

    /**
     * 作者头像
     */
    private String authorAvatarUrl;

}