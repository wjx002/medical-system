package com.dgut.medicalsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Medicine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dgut.medicalsystem.entity.dto.MedicineQuery;

/**
 * <p>
 * 药物库存表 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface MedicineService extends IService<Medicine> {

    Boolean updateStatus(Integer id);

    IPage<Medicine> queryDrugInventory(Page<Medicine> page, MedicineQuery medicineQuery);
}
