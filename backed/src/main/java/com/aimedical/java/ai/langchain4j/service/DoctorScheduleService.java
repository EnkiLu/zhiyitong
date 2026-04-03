package com.aimedical.java.ai.langchain4j.service;

import com.aimedical.java.ai.langchain4j.entity.DoctorSchedule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DoctorScheduleService extends IService<DoctorSchedule> {

    /**
     * 查询指定科室、日期、时段下有剩余号源的排班列表
     * （不限定医生，返回所有有空余号源的排班）
     */
    List<DoctorSchedule> queryAvailable(String department, String date, String time);

    /**
     * 查询指定医生在指定科室、日期、时段是否有剩余号源
     */
    boolean queryAvailableByDoctor(String department, String date, String time, String doctorName);
}
