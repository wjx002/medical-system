package com.dgut.medicalsystem.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Bill;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.entity.dto.Pagination;
import com.dgut.medicalsystem.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账单表 前端控制器
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService billService;

    @SaCheckLogin
    @RequestMapping("/checkBill")
    public IPage<Bill> checkBill(@RequestBody Pagination pagination) {
        try {
            int userid = Integer.parseInt(StpUtil.getLoginId().toString().substring(1));
            Page<Bill> page = new Page<>(pagination.getPage(), pagination.getSize());
            return billService.getBillListById(userid, page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SaCheckLogin
    @RequestMapping("payBill")
    public Map<String, Object> finishPayBill(@RequestBody Map<String, String> mapArg) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (billService.payBill(Long.parseLong(mapArg.get("id")))) {
            map.put("code", "200");
            map.put("msg", "缴费成功");
        } else {
            throw new Exception("缴费失败,暂无待缴账单");
        }
        return map;
    }
}
