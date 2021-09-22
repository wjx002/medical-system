package com.dgut.medicalsystem.service;

import com.dgut.medicalsystem.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 科室表
 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface DepartmentService extends IService<Department> {

    List<Department> getAll();
}
