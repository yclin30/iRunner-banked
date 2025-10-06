package com.yclin.irunnerbanked.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.yclin.irunnerbanked.mapper.ActivityMapper;
import com.yclin.irunnerbanked.model.domain.Activity;
import com.yclin.irunnerbanked.model.domain.User;
import com.yclin.irunnerbanked.model.request.ActivityAddRequest;
import com.yclin.irunnerbanked.service.ActivityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityService {

    private static final Gson gson = new Gson();

    @Override
    public long addActivity(ActivityAddRequest activityAddRequest, User loginUser) {
        // 1. 参数校验
        if (activityAddRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        if (loginUser == null) {
            throw new RuntimeException("用户未登录");
        }

        BigDecimal distance = activityAddRequest.getDistance();
        Integer duration = activityAddRequest.getDuration();

        if (distance == null || distance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("距离参数错误");
        }
        if (duration == null || duration <= 0) {
            throw new RuntimeException("时长参数错误");
        }

        // 2. 创建 Activity 实体对象
        Activity activity = new Activity();
        activity.setUserId(loginUser.getId());
        activity.setDistance(distance);
        activity.setDuration(duration);
        activity.setStartTime(activityAddRequest.getStartTime());

        // 3. 【关键】处理轨迹点，将其转换为 JSON 字符串
        if (activityAddRequest.getTrackPoints() != null && !activityAddRequest.getTrackPoints().isEmpty()) {
            String trackPointsJson = gson.toJson(activityAddRequest.getTrackPoints());
            activity.setTrackPoints(trackPointsJson);
        }

        // 4. 计算平均配速和卡路里（这里是简化示例）
        // 平均配速（分钟/公里）= (时长/60) / 距离
        if (distance.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal durationInMinutes = new BigDecimal(duration).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal avgSpeed = durationInMinutes.divide(distance, 2, BigDecimal.ROUND_HALF_UP);
            activity.setAvgSpeed(avgSpeed);
        }
        // 卡路里 ≈ 体重(kg) * 距离(km) * 1.036 (假设用户体重65kg)
        activity.setCalories(distance.multiply(new BigDecimal("65")).multiply(new BigDecimal("1.036")).intValue());


        // 5. 插入数据库
        boolean saveResult = this.save(activity);
        if (!saveResult) {
            throw new RuntimeException("创建运动记录失败，数据库错误");
        }

        return activity.getId();
    }

    @Override
    public IPage<Activity> listActivitiesByPage(Page<Activity> page, User loginUser) {
        if (loginUser == null) {
            throw new RuntimeException("用户未登录");
        }

        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", loginUser.getId());
        queryWrapper.orderByDesc("start_time");
        queryWrapper.select(Activity.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("track_points"));

        // MyBatis-Plus 会自动处理分页查询
        return this.page(page, queryWrapper);
    }
    @Override
    public Activity getActivityDetail(long activityId, User loginUser) {
        Activity activity = this.getById(activityId);
        if (activity == null) {
            throw new RuntimeException("记录不存在");
        }

        // 【安全】校验该记录是否属于当前登录用户
        if (!activity.getUserId().equals(loginUser.getId())) {
            throw new RuntimeException("无权访问该记录");
        }

        // 这里返回的是包含所有字段的完整 Activity 对象
        return activity;
    }
}