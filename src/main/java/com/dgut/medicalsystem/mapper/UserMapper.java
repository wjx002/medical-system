package com.dgut.medicalsystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.entity.dto.Progress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from progress_time where patient_id=#{id}")
    Progress checkProgressById(@Param("id") Integer id);


    @Select("select amount from availableDoctor_list where docId=#{docid}")
    String getQueueNumber(@Param("docid") int docid);
}
