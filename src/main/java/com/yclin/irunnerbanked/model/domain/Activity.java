package com.yclin.irunnerbanked.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 运动记录表
 * @TableName activity
 */
@TableName(value ="activity")
@Data
public class Activity {
    /**
     * 记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 距离（公里）
     */
    @TableField(value = "distance")
    private BigDecimal distance;

    /**
     * 时长（秒）
     */
    @TableField(value = "duration")
    private Integer duration;

    /**
     * 平均配速（分钟/公里）
     */
    @TableField(value = "avg_speed")
    private BigDecimal avgSpeed;

    /**
     * 消耗卡路里
     */
    @TableField(value = "calories")
    private Integer calories;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 轨迹点 (存储为JSON数组)
     */
    @TableField(value = "track_points")
    private String trackPoints;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
}