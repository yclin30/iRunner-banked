package com.yclin.irunnerbanked.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yclin.irunnerbanked.common.BaseResponse;
import com.yclin.irunnerbanked.common.ResultUtils;
import com.yclin.irunnerbanked.model.domain.Post;
import com.yclin.irunnerbanked.model.domain.User;
import com.yclin.irunnerbanked.model.request.PostAddRequest;
import com.yclin.irunnerbanked.model.vo.PostVO;
import com.yclin.irunnerbanked.service.PostService;
import com.yclin.irunnerbanked.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    /**
     * 发布新动态
     * @param postAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addPost(@RequestBody PostAddRequest postAddRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        
        long newPostId = postService.addPost(postAddRequest, loginUser);
        
        return ResultUtils.success(newPostId);
    }

    @GetMapping("/list")
    public BaseResponse<IPage<PostVO>> listPosts(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "10") long pageSize) {

        Page<Post> page = new Page<>(pageNum, pageSize);
        IPage<PostVO> postVOPage = postService.listPostVOByPage(page);

        return ResultUtils.success(postVOPage);
    }
}