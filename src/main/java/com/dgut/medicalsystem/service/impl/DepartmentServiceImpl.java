package com.dgut.medicalsystem.service.impl;

import com.dgut.medicalsystem.entity.Department;
import com.dgut.medicalsystem.mapper.DepartmentMapper;
import com.dgut.medicalsystem.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 科室表
 服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAll() {
        return departmentMapper.selectAll();
    }
}
