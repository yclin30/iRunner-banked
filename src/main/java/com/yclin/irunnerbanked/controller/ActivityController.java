package com.yclin.irunnerbanked.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yclin.irunnerbanked.common.BaseResponse;
import com.yclin.irunnerbanked.common.ResultUtils;
import com.yclin.irunnerbanked.model.domain.Activity;
import com.yclin.irunnerbanked.model.domain.User;
import com.yclin.irunnerbanked.model.request.ActivityAddRequest;
import com.yclin.irunnerbanked.service.ActivityService;
import com.yclin.irunnerbanked.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 运动记录接口
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private UserService userService;

    /**
     * 创建新的运动记录
     *
     * @param activityAddRequest 包含运动数据的请求体
     * @param request            用于获取当前登录用户
     * @return 新记录的ID
     */
    @PostMapping("/add")
    public BaseResponse<Long> addActivity(@RequestBody ActivityAddRequest activityAddRequest, HttpServletRequest request) {
        // 1. 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            // 在实际项目中，这应该由全局异常处理器或安全框架（如Spring Security）处理
            return ResultUtils.error(401, "未登录");
        }

        // 2. 调用 Service 层处理业务逻辑
        // 我们之前设计的 Service 会处理所有参数校验和数据转换
        long newActivityId = activityService.addActivity(activityAddRequest, loginUser);

        // 3. 返回成功响应
        return ResultUtils.success(newActivityId);
    }

    // 接下来你可以在这里添加 "获取运动列表"、"获取单次详情" 等接口
    /**
     * 分页获取当前用户的运动记录列表
     * @param pageNum   当前页码
     * @param pageSize  每页数量
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<IPage<Activity>> listActivities(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "10") long pageSize,
            HttpServletRequest request) {

        User loginUser = userService.getLoginUser(request);

        // 创建分页对象
        Page<Activity> page = new Page<>(pageNum, pageSize);

        IPage<Activity> activityPage = activityService.listActivitiesByPage(page, loginUser);

        return ResultUtils.success(activityPage);
    }

    /**
     * 获取单次运动详情
     * @param activityId 记录ID
     * @param request
     * @return
     */
    @GetMapping("/detail/{activityId}")
    public BaseResponse<Activity> getActivityDetail(@PathVariable long activityId, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);

        Activity activity = activityService.getActivityDetail(activityId, loginUser);

        return ResultUtils.success(activity);
    }
}