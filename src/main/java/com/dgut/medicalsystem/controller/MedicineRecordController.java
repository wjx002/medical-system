package com.dgut.medicalsystem.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Bill;
import com.dgut.medicalsystem.entity.MedicineRecord;
import com.dgut.medicalsystem.entity.dto.Pagination;
import com.dgut.medicalsystem.service.MedicineRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 药物列表 前端控制器
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@RestController
@RequestMapping("/medicine-record")
public class MedicineRecordController {
}
