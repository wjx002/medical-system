package com.dgut.medicalsystem.mapper;

import com.dgut.medicalsystem.entity.MedicalHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface MedicalHistoryMapper extends BaseMapper<MedicalHistory> {

    /**
     * 根据医生id分页查找挂到该医生下的未结束看病流程的病人列表
     * @param userId 病人Id
     * @return 病人最新病历信息,以此判断病人现在的状态
     */
    @Select("select * from medical_history a where a.patient_id=#{id} and ISNULL(a.record_date)")
    MedicalHistory getLatestHistory(@Param("id") Integer userId);


    @Select("SELECT doctor_id from medical_history where record_id='${rid}' and patient_id = '${uid}';")
    Integer getDrByRecord(@Param("rid") Long rid, @Param("uid") Integer uid);
}
