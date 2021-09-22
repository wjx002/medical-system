package com.dgut.medicalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Medicine;
import com.dgut.medicalsystem.entity.dto.MedicineQuery;
import com.dgut.medicalsystem.mapper.MedicineMapper;
import com.dgut.medicalsystem.service.MedicineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 药物库存表 服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine> implements MedicineService {

    @Autowired
    MedicineMapper medicineMapper;



    @Override
    public Boolean updateStatus(Integer id) {
        int i = medicineMapper.deleteById(id);
        if (i==0){
            return false;
        }
        return true;
    }

    @Override
    public IPage<Medicine> queryDrugInventory(Page<Medicine> page, MedicineQuery medicineQuery) {

        QueryWrapper<Medicine> wrapper = new QueryWrapper<>();
        if (medicineQuery.getMedicineStatus()==null) {
            wrapper.like("medicine_name", medicineQuery.getMedicineName())
                    .like("medical_type", medicineQuery.getMedicalType())
                    .orderByDesc("medicine_status");
        }else {
            wrapper.like("medicine_name", medicineQuery.getMedicineName())
                    .like("medical_type", medicineQuery.getMedicalType())
                    .like("medicine_status", medicineQuery.getMedicineStatus())
                    .orderByDesc("medicine_status");

        }
        IPage<Medicine> medicineIPage = medicineMapper.queryDrugInventory(page,wrapper);
        return medicineIPage;
    }
}
