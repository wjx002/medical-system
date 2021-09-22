package com.dgut.medicalsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 账单表 Mapper 接口
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Mapper
public interface BillMapper extends BaseMapper<Bill> {

    @Select("select * from bill ${ew.customSqlSegment} order by IF(ISNULL(bill_date),0,1),bill_date desc")
    IPage<Bill> getBillLists(Page<Bill> page, @Param(Constants.WRAPPER) QueryWrapper wrapper);


    @Select("SELECT bill_id FROM medical_history where record_id = #{rid}")
    Integer getBillByRecord(@Param("rid") Long rid);
}
