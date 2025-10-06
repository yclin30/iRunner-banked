package com.yclin.irunnerbanked.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yclin.irunnerbanked.model.domain.Activity;
import com.yclin.irunnerbanked.model.domain.User;
import com.yclin.irunnerbanked.model.request.ActivityAddRequest;

public interface ActivityService extends IService<Activity> {

    /**
     * 创建新的运动记录
     *
     * @param activityAddRequest 运动数据
     * @param loginUser          当前登录用户
     * @return 新记录的ID
     */
    long addActivity(ActivityAddRequest activityAddRequest, User loginUser);
    /**
     * 分页获取当前用户的运动记录
     *
     * @param page      分页参数
     * @param loginUser 当前登录用户
     * @return
     */
    IPage<Activity> listActivitiesByPage(Page<Activity> page, User loginUser);
    /**
     * 获取单次运动详情
     * @param activityId 记录ID
     * @param loginUser  当前登录用户
     * @return
     */
    Activity getActivityDetail(long activityId, User loginUser);
}