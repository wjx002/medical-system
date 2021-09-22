package com.dgut.medicalsystem.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Appointment;
import com.dgut.medicalsystem.entity.dto.AppointmentApply;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 挂号表 前端控制器
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @SaCheckLogin
    @RequestMapping("appointment_list")
    public IPage<AppointmentInfo> getAppointmentList(@RequestBody AppointmentInfo appointmentInfo) {
        try {
            Page<AppointmentInfo> page = new Page<>(appointmentInfo.getPage(), appointmentInfo.getSize());
            return appointmentService.getAppointmentList(page, appointmentInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SaCheckLogin
    @RequestMapping("appoint")
    public Map<String, Object> finishAppoint(@RequestBody AppointmentApply appointmentApply) throws Exception {
//        try {
        Map<String, Object> map = new HashMap<>();
        boolean res = appointmentService.createAppointment(appointmentApply);
        if (res) {
            map.put("code", "200");
            map.put("msg", "挂号成功");
        } else {
            throw new Exception("挂号失败,有未完成的挂号记录");
        }
        return map;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Map<String, Object> map = new HashMap<>();
//            map.put("ret", "1");
//            map.put("description", e.getMessage());
//            return map;
//        }
    }

}
