package com.aimedical.java.ai.langchain4j.service.impl;

import com.aimedical.java.ai.langchain4j.entity.DoctorSchedule;
import com.aimedical.java.ai.langchain4j.mapper.DoctorScheduleMapper;
import com.aimedical.java.ai.langchain4j.service.DoctorScheduleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DoctorScheduleServiceImpl
        extends ServiceImpl<DoctorScheduleMapper, DoctorSchedule>
        implements DoctorScheduleService {

    @Override
    public List<DoctorSchedule> queryAvailable(String department, String date, String time) {
        LambdaQueryWrapper<DoctorSchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DoctorSchedule::getDepartment, department)
               .eq(DoctorSchedule::getDate, date)
               .eq(DoctorSchedule::getTime, time)
               // booked < total 表示还有剩余号源
               .apply("booked < total");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public boolean queryAvailableByDoctor(String department, String date, String time, String doctorName) {
        LambdaQueryWrapper<DoctorSchedule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DoctorSchedule::getDepartment, department)
               .eq(DoctorSchedule::getDate, date)
               .eq(DoctorSchedule::getTime, time)
               .eq(StringUtils.hasText(doctorName), DoctorSchedule::getDoctorName, doctorName)
               .apply("booked < total");
        return baseMapper.selectCount(wrapper) > 0;
    }
}
