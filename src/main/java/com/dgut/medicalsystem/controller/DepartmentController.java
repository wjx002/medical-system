package com.dgut.medicalsystem.controller;


import com.dgut.medicalsystem.entity.Department;
import com.dgut.medicalsystem.entity.MedicalStaff;
import com.dgut.medicalsystem.entity.dto.DepartInfo;
import com.dgut.medicalsystem.service.DepartmentService;
import com.dgut.medicalsystem.service.MedicalStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 科室表
 前端控制器
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MedicalStaffService medicalStaffService;

    /*
    * 获取所有部门门诊及其包含的医生
    * */
    @RequestMapping("/getAllDepart")
    public Map<String,Object> getAllDepart(){
        Map<String,Object> map = new HashMap<>();
        List<DepartInfo> departList = new ArrayList<>();
        try{
            List<Department> departs = departmentService.getAll();
            if (departs!=null){
                for(Department item:departs){
                    List<MedicalStaff> staffs = medicalStaffService.getByDepartment(item.getName());
                    departList.add(new DepartInfo(item,staffs));
                }
                map.put("ret",0);
                map.put("data",departList);
                map.put("msg","获取成功");
            }else{
                map.put("ret",2);
                map.put("data",null);
                map.put("msg","暂无数据");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("ret",1);
            map.put("data",null);
            map.put("msg","系统错误");
        }
        return map;
    }



}
