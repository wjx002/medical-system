package com.dgut.medicalsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Bill;
import com.dgut.medicalsystem.entity.MedicineRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 药物列表 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface MedicineRecordService extends IService<MedicineRecord> {
    List<MedicineRecord> getByRecord(Long rid);
}
