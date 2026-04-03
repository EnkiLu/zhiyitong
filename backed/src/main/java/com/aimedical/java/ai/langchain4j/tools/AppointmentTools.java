package com.aimedical.java.ai.langchain4j.tools;
import com.aimedical.java.ai.langchain4j.entity.Appointment;
import com.aimedical.java.ai.langchain4j.entity.DoctorSchedule;
import com.aimedical.java.ai.langchain4j.service.AppointmentService;
import com.aimedical.java.ai.langchain4j.service.DoctorScheduleService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class AppointmentTools {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorScheduleService doctorScheduleService;
        @Tool(name="预约挂号", value = "根据参数，先执行工具方法queryDepartment查询是否可预约，并直接给用户回答是否可预约，并让用户确认所有预约信息，用户确认后再进行预约。如果用户没有提供具体的医生姓名，请从向量存储中找到一位医生。")
            public String bookAppointment(Appointment appointment){
            //查找数据库中是否包含对应的预约记录
                Appointment appointmentDB = appointmentService.getOne(appointment);
                if(appointmentDB == null){
                appointment.setId(null);//防止大模型幻觉设置了id
                if(appointmentService.save(appointment)){
                return "预约成功，并返回预约详情";
                }else{
                return "预约失败";
              }
            }
            return "您在相同的科室和时间已有预约";
        }


        @Tool(name="取消预约挂号", value = "根据参数，查询预约是否存在，如果存在则删除预约记录并返回取消预约成功，否则返回取消预约失败")
        public String cancelAppointment(Appointment appointment){
            Appointment appointmentDB = appointmentService.getOne(appointment);
                if(appointmentDB != null){
                    //删除预约记录
                    if(appointmentService.removeById(appointmentDB.getId())){
                        return "取消预约成功";
                    }else{
                        return "取消预约失败";
                    }
                }
                //取消失败
                return "您没有预约记录，请核对预约科室和时间";
        }
        @Tool(name = "查询是否有号源", value = "根据科室名称，日期，时间和医生查询是否有号源，并返回给用户")
        public String queryDepartment(
                @P(value = "科室名称") String name,
                @P(value = "日期") String date,
                @P(value = "时间，可选值：上午、下午") String time,
                @P(value = "医生名称", required = false) String doctorName
        ) {
            System.out.println("查询是否有号源 - 科室：" + name + "，日期：" + date + "，时段：" + time + "，医生：" + doctorName);

            // 1. 指定了医生：直接判断该医生是否有剩余号源
            if (StringUtils.hasText(doctorName)) {
                boolean available = doctorScheduleService.queryAvailableByDoctor(name, date, time, doctorName);
                if (available) {
                    return String.format("%s %s %s %s 有号源，可以预约", name, date, time, doctorName);
                } else {
                    return String.format("%s %s %s %s 暂无号源或未安排出诊，请选择其他时间或医生", name, date, time, doctorName);
                }
            }

            // 2. 未指定医生：查询该科室当天时段所有有剩余号源的排班
            List<DoctorSchedule> schedules = doctorScheduleService.queryAvailable(name, date, time);
            if (schedules.isEmpty()) {
                return String.format("%s %s %s 暂无可预约号源，请选择其他时间", name, date, time);
            }

            // 拼接可预约医生列表返回给大模型，由大模型告知用户
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s %s %s 有以下医生可预约：\n", name, date, time));
            for (DoctorSchedule s : schedules) {
                int remaining = s.getTotal() - s.getBooked();
                sb.append(String.format("  - %s（剩余 %d 个号）\n", s.getDoctorName(), remaining));
            }
            return sb.toString();
        }
}