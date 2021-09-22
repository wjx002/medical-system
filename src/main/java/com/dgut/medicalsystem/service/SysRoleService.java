package com.dgut.medicalsystem.service;

import com.dgut.medicalsystem.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 通过name_zh查id
     * @param name_zh
     * @return
     */
     int getIdbyname_zh(String name_zh);
}
