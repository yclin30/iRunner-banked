package com.yclin.irunnerbanked.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yclin.irunnerbanked.model.domain.Post;
import com.yclin.irunnerbanked.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;

public interface PostMapper extends BaseMapper<Post> {

    /**
     * 自定义分页查询，联表查询 post 和 user
     * @param page 分页对象
     * @param queryWrapper 查询条件
     * @return
     */
    IPage<PostVO> selectPostVOPage(Page<?> page, @Param(Constants.WRAPPER) Wrapper<Post> queryWrapper);
}