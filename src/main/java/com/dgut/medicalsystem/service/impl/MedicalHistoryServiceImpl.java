package com.dgut.medicalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.MedicalHistory;
import com.dgut.medicalsystem.mapper.MedicalHistoryMapper;
import com.dgut.medicalsystem.service.MedicalHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class MedicalHistoryServiceImpl extends ServiceImpl<MedicalHistoryMapper, MedicalHistory> implements MedicalHistoryService {

    @Autowired
    MedicalHistoryMapper medicalHistoryMapper;

    @Override
    public MedicalHistory getLatestHistory(Integer userId) {
        MedicalHistory medicalHistory = medicalHistoryMapper.getLatestHistory(userId);
        return medicalHistory;
    }

    @Override
    public IPage<MedicalHistory> getUserAllHistory(Integer userId,Integer page,Integer size) {
        Page<MedicalHistory> historyPage = new Page<>(page,size);
        QueryWrapper<MedicalHistory>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id",userId).orderByDesc("record_id").isNotNull("record_date");
        IPage<MedicalHistory> list = medicalHistoryMapper.selectPage(historyPage,queryWrapper);
        return list;
    }

    @Override
    public Integer getDrByRecord(Long rid, Integer uid) {
        return medicalHistoryMapper.getDrByRecord(rid, uid);
    }
}
