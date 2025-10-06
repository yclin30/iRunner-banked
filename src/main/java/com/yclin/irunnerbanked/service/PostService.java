package com.yclin.irunnerbanked.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yclin.irunnerbanked.model.domain.Post;
import com.yclin.irunnerbanked.model.domain.User;
import com.yclin.irunnerbanked.model.request.PostAddRequest;
import com.yclin.irunnerbanked.model.vo.PostVO;

/**
* @author yclin
* @description 针对表【post(社区动态表)】的数据库操作Service
* @createDate 2025-10-06 18:00:43
*/
public interface PostService extends IService<Post> {

    /**
     * 发布新动态
     * @param postAddRequest 动态内容
     * @param loginUser 当前登录用户
     * @return 新动态的ID
     */
    long addPost(PostAddRequest postAddRequest, User loginUser);

    IPage<PostVO> listPostVOByPage(Page<Post> page);


    /**
     * 删除动态
     * @param postId    动态ID
     * @param loginUser 当前登录用户
     * @return 是否删除成功
     */
    boolean deletePost(long postId, User loginUser);
}
