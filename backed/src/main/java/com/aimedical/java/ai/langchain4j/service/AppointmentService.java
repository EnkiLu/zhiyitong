package com.aimedical.java.ai.langchain4j.service;

import com.aimedical.java.ai.langchain4j.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AppointmentService extends IService<Appointment> {
Appointment getOne(Appointment appointment);
}
