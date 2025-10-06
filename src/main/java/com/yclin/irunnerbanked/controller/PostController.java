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
    /**
     * 删除动态
     *
     * @param postId  要删除的动态ID
     * @param request 用于获取当前用户
     * @return
     */
    @DeleteMapping("/delete/{postId}")
    public BaseResponse deletePost(@PathVariable long postId, HttpServletRequest request) {
        // 1. 获取当前登录用户
        User loginUser = userService.getLoginUser(request);

        // 2. 调用 Service 层执行删除和权限校验
        boolean result = postService.deletePost(postId, loginUser);

        // 3. 返回结果
        if (!result) {
            return ResultUtils.error(500, "删除失败");
        }
        return ResultUtils.success(true);
    }
}