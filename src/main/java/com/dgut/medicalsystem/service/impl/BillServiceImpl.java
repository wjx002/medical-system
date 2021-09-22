package com.dgut.medicalsystem.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Bill;
import com.dgut.medicalsystem.entity.MedicalHistory;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.mapper.BillMapper;
import com.dgut.medicalsystem.service.BillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dgut.medicalsystem.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 账单表 服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    @Autowired
    BillMapper billMapper;
    @Autowired
    MedicalHistoryService medicalHistoryService;

    @Override
    public IPage<Bill> getBillListById(int userid, Page<Bill> page) {
        List<MedicalHistory> res = medicalHistoryService.list(new LambdaQueryWrapper<MedicalHistory>()
                .eq(MedicalHistory::getPatientId, userid));

        List<Long> list = new ArrayList<>(res.size());
        for (MedicalHistory i : res) {
            list.add(i.getBillId());
        }
        System.out.println(list);
        return billMapper.getBillLists(page, new QueryWrapper<Bill>().in("id", list));
    }

    @Override
    public boolean payBill(Long billId) {
        int userid = Integer.parseInt(StpUtil.getLoginId().toString().substring(1));
        MedicalHistory res = medicalHistoryService.getOne(new QueryWrapper<MedicalHistory>()
                .eq("patient_id", userid)
                .isNull("record_date"));
        if (res.getBillId().equals(billId)) {
            Bill bill = new Bill();
            bill.setBillDate(LocalDateTime.now());
            return update(bill, new QueryWrapper<Bill>().eq("id", billId));
        }
        return false;
    }

    @Override
    public Integer getBillByRecord(Long rid) {
        return billMapper.getBillByRecord(rid);
    }
}
