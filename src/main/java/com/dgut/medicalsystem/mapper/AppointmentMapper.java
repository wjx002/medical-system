package com.dgut.medicalsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.dgut.medicalsystem.entity.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 挂号表 Mapper 接口
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
    @Select("select docId,name,subject,proTitle,roomNo,amount from availableDoctor_list ${ew.customSqlSegment}")
    IPage<AppointmentInfo> getAppointmentList(IPage<AppointmentInfo> page,
                                              @Param(Constants.WRAPPER) QueryWrapper wrapper);

    @Select("select appointment_id from medical_history where record_id = #{rid} ")
    Integer getByRecord(@Param("rid") Long rid);
}
