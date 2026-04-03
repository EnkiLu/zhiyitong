package com.aimedical.java.ai.langchain4j.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 医生排班表
 * 对应数据库表 doctor_schedule
 *
 * 建表SQL：
 * CREATE TABLE doctor_schedule (
 *   id          BIGINT AUTO_INCREMENT PRIMARY KEY,
 *   department  VARCHAR(64)  NOT NULL COMMENT '科室名称',
 *   doctor_name VARCHAR(32)  NOT NULL COMMENT '医生姓名',
 *   date        VARCHAR(16)  NOT NULL COMMENT '排班日期，格式 yyyy-MM-dd',
 *   time        VARCHAR(8)   NOT NULL COMMENT '排班时段：上午 / 下午',
 *   total       INT          NOT NULL DEFAULT 20 COMMENT '总号源数',
 *   booked      INT          NOT NULL DEFAULT 0  COMMENT '已预约数',
 *   INDEX idx_dept_date_time (department, date, time)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生排班表';
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("doctor_schedule")
public class DoctorSchedule {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 科室名称 */
    private String department;

    /** 医生姓名 */
    private String doctorName;

    /** 排班日期，格式 yyyy-MM-dd */
    private String date;

    /** 排班时段：上午 / 下午 */
    private String time;

    /** 总号源数 */
    private Integer total;

    /** 已预约数 */
    private Integer booked;
}
