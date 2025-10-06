package com.yclin.irunnerbanked.model.request;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 创建运动记录请求体
 */
@Data
public class ActivityAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 距离（公里）
     */
    private BigDecimal distance;

    /**
     * 时长（秒）
     */
    private Integer duration;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 轨迹点列表
     * 前端会传来一个包含多个坐标点的数组
     */
    private List<TrackPoint> trackPoints;

    /**
     * 内部类，用于表示单个轨迹点
     */
    @Data
    public static class TrackPoint {
        private Double latitude;  // 纬度
        private Double longitude; // 经度
        private Long timestamp;   // 时间戳
    }
}