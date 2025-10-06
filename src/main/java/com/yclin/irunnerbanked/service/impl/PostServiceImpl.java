package com.yclin.irunnerbanked.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.yclin.irunnerbanked.mapper.PostMapper;
import com.yclin.irunnerbanked.model.domain.Activity;
import com.yclin.irunnerbanked.model.domain.Post;
import com.yclin.irunnerbanked.model.domain.User;
import com.yclin.irunnerbanked.model.request.PostAddRequest;
import com.yclin.irunnerbanked.model.vo.PostVO;
import com.yclin.irunnerbanked.service.ActivityService;
import com.yclin.irunnerbanked.service.PostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
        implements PostService {

    @Resource
    private ActivityService activityService; // 注入 ActivityService 用于校验

    private static final Gson gson = new Gson();

    @Override
    public long addPost(PostAddRequest postAddRequest, User loginUser) {
        // 1. 参数校验
        if (postAddRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        if (StringUtils.isBlank(postAddRequest.getContent())) {
            throw new RuntimeException("动态内容不能为空");
        }

        // 2. 【重要】业务和安全校验
        Long activityId = postAddRequest.getActivityId();
        if (activityId != null && activityId > 0) {
            Activity activity = activityService.getById(activityId);
            if (activity == null) {
                throw new RuntimeException("关联的运动记录不存在");
            }
            // 检查该运动记录是否属于当前登录用户
            if (!activity.getUserId().equals(loginUser.getId())) {
                throw new RuntimeException("不能关联他人的运动记录");
            }
        }

        // 3. 创建 Post 实体
        Post post = new Post();
        post.setUserId(loginUser.getId());
        post.setContent(postAddRequest.getContent());
        post.setActivityId(activityId);

        // 4. 处理图片URL列表
        if (postAddRequest.getImageUrls() != null && !postAddRequest.getImageUrls().isEmpty()) {
            post.setImageUrls(gson.toJson(postAddRequest.getImageUrls()));
        }

        // 5. 存入数据库
        boolean saveResult = this.save(post);
        if (!saveResult) {
            throw new RuntimeException("发布动态失败，数据库错误");
        }
        return post.getId();
    }

    @Override
    public IPage<PostVO> listPostVOByPage(Page<Post> page) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        // 按创建时间倒序排列，最新的在最前面
        queryWrapper.orderByDesc("create_time");

        // 调用我们自定义的 Mapper 方法
        return this.baseMapper.selectPostVOPage(page, queryWrapper);
    }
}