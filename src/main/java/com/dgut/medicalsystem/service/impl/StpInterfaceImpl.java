package com.dgut.medicalsystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.dgut.medicalsystem.mapper.MedicalStaffMapper;
import com.dgut.medicalsystem.service.MedicalStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.dev33.satoken.stp.StpInterface;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    MedicalStaffService medicalStaffService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();
        list.add("101");
        list.add("user-add");
        list.add("user-delete");
        list.add("user-update");
        list.add("user-get");
        list.add("article-get");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        //分割role_type为前缀,role_id为user表id主键
        String role_type = loginId.toString().substring(0, 1);
        String user_id = loginId.toString().substring(1);
        if (role_type.equals("P")) {
            list.add("Patient");
        } else {
            list = medicalStaffService.getRoles(user_id);
        }
        return list;
    }

}

