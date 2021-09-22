package com.dgut.medicalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Bill;
import com.dgut.medicalsystem.entity.MedicalHistory;
import com.dgut.medicalsystem.entity.MedicineRecord;
import com.dgut.medicalsystem.mapper.BillMapper;
import com.dgut.medicalsystem.mapper.MedicineRecordMapper;
import com.dgut.medicalsystem.service.MedicalHistoryService;
import com.dgut.medicalsystem.service.MedicineRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 药物列表 服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class MedicineRecordServiceImpl extends ServiceImpl<MedicineRecordMapper, MedicineRecord> implements MedicineRecordService {

    @Autowired
    MedicineRecordMapper medicineRecordMapper;

    @Override
    public List<MedicineRecord> getByRecord(Long rid) {
        return medicineRecordMapper.getMedicineListByRecordId(rid);
    }

}
