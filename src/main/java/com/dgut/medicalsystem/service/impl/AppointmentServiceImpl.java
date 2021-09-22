package com.dgut.medicalsystem.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Appointment;
import com.dgut.medicalsystem.entity.Bill;
import com.dgut.medicalsystem.entity.MedicalHistory;
import com.dgut.medicalsystem.entity.User;
import com.dgut.medicalsystem.entity.dto.AppointmentApply;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.mapper.AppointmentMapper;
import com.dgut.medicalsystem.service.AppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dgut.medicalsystem.service.BillService;
import com.dgut.medicalsystem.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * <p>
 * 挂号表 服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
    @Autowired
    AppointmentMapper appointmentMapper;
    @Autowired
    BillService billService;
    @Autowired
    MedicalHistoryService medicalHistoryService;

    @Override
    public IPage<AppointmentInfo> getAppointmentList(Page<AppointmentInfo> page, AppointmentInfo appointmentInfo) {
        QueryWrapper<AppointmentInfo> wrapper = new QueryWrapper<>();
        wrapper.like("subject", appointmentInfo.getSubject())
                .like("proTitle", appointmentInfo.getProTitle())
                .like("name", appointmentInfo.getName());
        return appointmentMapper.getAppointmentList(page, wrapper);
    }

    @Transactional
    @Override
    public boolean createAppointment(AppointmentApply appointmentApply) throws Exception {
        //获取病人ID
        int userid = Integer.parseInt(StpUtil.getLoginId().toString().substring(1));
        //增加挂号信息到挂号信息表
        //没有未结束挂号信息
        if (medicalHistoryService.getLatestHistory(userid) == null) {
            Appointment appointment = new Appointment();
            LocalDateTime dateTime = LocalDateTime.now();
            appointment.setAppointmentDate(dateTime);
            appointment.setSymptom(appointmentApply.getSymptom());
            Long appo_id = userid + dateTime.toEpochSecond(ZoneOffset.of("+8"));
            appointment.setId(appo_id);
            save(appointment);
            //新增一条账单记录到账单表
            Long bill_id = userid + LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            Bill bill = new Bill();
            bill.setAppoPrice(appointmentApply.getAppoPrice());
            bill.setId(bill_id);
            billService.save(bill);
            //新增一条病历记录到病历表
            MedicalHistory medicalHistory = new MedicalHistory();
            medicalHistory.setAppointmentId(appo_id);
            medicalHistory.setBillId(bill_id);
            medicalHistory.setDoctorId(Integer.parseInt(appointmentApply.getDocId()));
            medicalHistory.setPatientId(userid);
            medicalHistoryService.save(medicalHistory);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer getByRecord(Long rid) {
        return appointmentMapper.getByRecord(rid);
    }

    @Override
    public Appointment getLatestByUser(Integer uid) {
        MedicalHistory latestHistory = medicalHistoryService.getLatestHistory(uid);
        return appointmentMapper.selectById(latestHistory.getAppointmentId());
    }
}
