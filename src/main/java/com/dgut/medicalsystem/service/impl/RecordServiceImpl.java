package com.dgut.medicalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dgut.medicalsystem.entity.MedicalHistory;
import com.dgut.medicalsystem.entity.MedicalStaff;
import com.dgut.medicalsystem.entity.Record;
import com.dgut.medicalsystem.mapper.RecordMapper;
import com.dgut.medicalsystem.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dgut.medicalsystem.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 诊断记录表 服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    MedicalHistoryService medicalHistoryService;

    @Autowired
    MedicalStaffService medicalStaffService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    BillService billService;

    @Override
    public List<Map<String, Object>> getUserAllRecord(Integer userId,Integer page,Integer size) {
        IPage<MedicalHistory> historyIPageList = medicalHistoryService.getUserAllHistory(userId,page,size);
        List<MedicalHistory> historyList = historyIPageList.getRecords();
        List<Map<String, Object>> list = new ArrayList<>();
        for (MedicalHistory history:historyList) {
            Record record = this.getOne(new LambdaQueryWrapper<Record>().eq(Record::getId,history.getRecordId()));
            Map<String, Object> map = new HashMap<>();
            map.put("id",record.getId());
            map.put("status",record.getCureStatus());
            map.put("doctor",medicalStaffService.getOne(new LambdaQueryWrapper<MedicalStaff>().eq(MedicalStaff::getId,history.getDoctorId())).getName());
            map.put("cureDescription",record.getCureDescription());
            map.put("recordDate", DateFormatUtil.dateFormat(record.getRecordDate()));
            list.add(map);
        }
        System.out.println(list);
        return list;
    }

    @Override
    public Long getUserAllRecordCount(Integer userId) {
        IPage<MedicalHistory> historyIPageList = medicalHistoryService.getUserAllHistory(userId,1,1);
        return historyIPageList.getTotal();
    }

    @Override
    public Integer getDrByRecord(Long rid,Integer uid) {
        return medicalHistoryService.getDrByRecord(rid,uid);
    }

    @Override
    public Integer getAppoByRecord(Long rid) {
        return appointmentService.getByRecord(rid);
    }

    @Override
    public Integer getBillByRecord(Long rid) {

        return billService.getBillByRecord(rid);
    }
}
