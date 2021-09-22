package com.dgut.medicalsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Medicine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 药物库存表 Mapper 接口
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Mapper
public interface MedicineMapper extends BaseMapper<Medicine> {

    @Select("select * from medicine ${ew.customSqlSegment}")
    IPage<Medicine> queryDrugInventory(Page<Medicine> medicinePage,@Param(Constants.WRAPPER) QueryWrapper wrapper);
}
