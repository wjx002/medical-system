package com.dgut.medicalsystem.mapper;

import com.dgut.medicalsystem.entity.MedicineRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dgut.medicalsystem.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 药物列表 Mapper 接口
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Mapper
public interface MedicineRecordMapper extends BaseMapper<MedicineRecord> {

    /**
     * 通过诊断记录获取药物列表
     * @param recordId 诊断记录号
     * @return 该诊断记录下的药物列表
     */
    @Select("select * from medicine_record where record_id = #{id}")
    List<MedicineRecord> getMedicineListByRecordId(@Param("id")Long recordId);

    @Select("select * from medicine_record where record_id = (select record_id from medical_history where patient_id = #{userId} and ISNULL(record_date))")
    MedicineRecord queryMedicineById(Integer userId);
}
