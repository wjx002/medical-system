package com.dgut.medicalsystem.service.impl;

import com.dgut.medicalsystem.entity.SysRole;
import com.dgut.medicalsystem.mapper.SysRoleMapper;
import com.dgut.medicalsystem.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
   @Autowired
   SysRoleMapper sysRoleMapper;
    public int getIdbyname_zh(String name_zh){
        int id = sysRoleMapper.getIdByName_zh(name_zh);
        return id;
    }
}
