package com.dgut.medicalsystem.mapper;

import com.dgut.medicalsystem.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
@Select("select id from sys_role where name_zh = '${name_zh}'")
    int getIdByName_zh(String name_zh);
}
