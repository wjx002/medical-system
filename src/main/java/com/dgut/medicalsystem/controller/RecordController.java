package com.dgut.medicalsystem.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.*;
import com.dgut.medicalsystem.entity.Record;
import com.dgut.medicalsystem.entity.dto.Pagination;
import com.dgut.medicalsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService recordService;
    @Autowired
    UserService userService;
    @Autowired
    MedicalStaffService medicalStaffService;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    MedicineRecordService medicineRecordService;
    @Autowired
    BillService billService;

    @SaCheckLogin
    @RequestMapping("checkRecords")
    public Map<String, Object> showTotalHistory(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        int userId = Integer.parseInt(StpUtil.getLoginId().toString().substring(1));
        Map<String, Object> map = new HashMap<>();
        if (!userService.checkUserById(userId)) {
            map.put("ret", 2);
            map.put("description", "查无此人");
            return map;
        }
        List<Map<String, Object>> records = recordService.getUserAllRecord(userId, page, size);
        if (records.isEmpty()) {
            map.put("ret", 1);
            map.put("description", "该用户无既往病史");
        } else {
            map.put("ret", 0);
            map.put("description", "查找成功");
            map.put("list", records);
            map.put("count", recordService.getUserAllRecordCount(userId));
        }
        return map;
    }

    /*
    * 通过诊断记录获取详细细节
    * */
    @SaCheckLogin
    @RequestMapping("/getDetail")
    public Map<String,Object> getDoctorByRecord(@RequestParam("rid") Long rid){

        Map<String, Object> map = new HashMap<>();

        try{
            Integer uid = Integer.valueOf(StpUtil.getLoginId().toString().substring(1));
            User user = userService.getById(uid);
            user.setPassword(null);
            user.setIdno(null);

            Integer drId = recordService.getDrByRecord(rid,uid);
            MedicalStaff dr =  medicalStaffService.getNeccessaryInfo(drId);

            Integer appoId = recordService.getAppoByRecord(rid);
            Appointment appointment = appointmentService.getById(appoId);

            Record record = recordService.getById(rid);

            List<MedicineRecord> medicineRecords = medicineRecordService.getByRecord(rid);

            Integer bid = recordService.getBillByRecord(rid);
            Bill bill = billService.getById(bid);

            if (appointment==null||record==null||medicineRecords==null||bill==null){
                map.put("ret",-1);
                map.put("msg","获取失败,请稍后再试");
            }else{
                map.put("ret",0);
                map.put("user",user);
                map.put("doctor",dr);
                map.put("appointment",appointment);
                map.put("record",record);
                map.put("medicines",medicineRecords);
                map.put("bill",bill);
                map.put("msg","获取成功");
            }

        }catch (Exception e){
            e.printStackTrace();
            map.put("ret",1);
            map.put("msg","服务错误,请稍后再试");
        }

        return map;
    }

}
