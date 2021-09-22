package com.dgut.medicalsystem.mapper;

import com.dgut.medicalsystem.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 科室表
 Mapper 接口
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    @Select("SELECT * FROM department")
    List<Department> selectAll();
}
