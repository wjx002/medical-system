package com.dgut.medicalsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Bill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;

import java.util.List;

/**
 * <p>
 * 账单表 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface BillService extends IService<Bill> {

    IPage<Bill> getBillListById(int userid, Page<Bill> page);

    boolean payBill(Long billId);

    Integer getBillByRecord(Long rid);
}
