package com.dgut.medicalsystem.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dgut.medicalsystem.entity.Information;
import com.dgut.medicalsystem.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@RestController
@RequestMapping("/information")
public class InformationController {

    @Autowired
    InformationService informationService;

    @SaCheckLogin
    @SaCheckRole("superadmin")
    @PostMapping("/add")
    public Map<String,Object> addInformation(@RequestBody Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        if (informationService.addInformation(data.get("information"))) {
            map.put("ret",0);
            map.put("description","添加成功");
        } else {
            map.put("ret", "1");
            map.put("description", "添加失败");
        }
        return map;
    }

    @GetMapping("/search")
    public Map<String,Object> searchInformation(@RequestParam("page")Integer page, @RequestParam("size") Integer size) {
        Map<String, Object> map = new HashMap<>();
        IPage<Information> iPage= informationService.searchInformation(page,size);
        if (iPage.getTotal() != 0) {
            map.put("ret",0);
            map.put("description","查找成功");
            map.put("list",iPage.getRecords());
            map.put("total",iPage.getTotal());
        } else {
            map.put("ret", "1");
            map.put("description", "查找失败或查无资讯");
        }
        return map;
    }

    @SaCheckLogin
    @SaCheckRole("superadmin")
    @PostMapping("/update")
    public Map<String, Object> updateInformation(@RequestBody Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.parseInt(data.get("id"));
        String info = data.get("information");
        if (informationService.updateInformation(id,info)) {
            map.put("ret",0);
            map.put("description","修改成功");
        } else {
            map.put("ret", "1");
            map.put("description", "修改失败");
        }
        return map;
    }

    @SaCheckLogin
    @SaCheckRole("superadmin")
    @PostMapping("/delete")
    public Map<String, Object> deleteInformation(@RequestBody Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.parseInt(data.get("id"));
        if (informationService.deleteInformation(id)) {
            map.put("ret",0);
            map.put("description","删除成功");
        } else {
            map.put("ret", "1");
            map.put("description", "删除失败");
        }
        return map;
    }
}
