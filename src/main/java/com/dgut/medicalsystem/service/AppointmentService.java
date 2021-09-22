package com.dgut.medicalsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dgut.medicalsystem.entity.dto.AppointmentApply;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;

import java.util.List;

/**
 * <p>
 * 挂号表 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface AppointmentService extends IService<Appointment> {
    /**
     * 病人查看挂号系统信息
     */
    IPage<AppointmentInfo> getAppointmentList(Page<AppointmentInfo> page, AppointmentInfo appointmentInfo);


    /**
     * 病人挂号
     */
    boolean createAppointment(AppointmentApply appointmentApply) throws Exception;

    Integer getByRecord(Long rid);

    Appointment getLatestByUser(Integer uid);
}
