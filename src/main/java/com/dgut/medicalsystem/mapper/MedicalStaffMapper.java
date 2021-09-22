package com.dgut.medicalsystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.MedicalStaff;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dgut.medicalsystem.entity.User;
import com.dgut.medicalsystem.entity.dto.HotDoctor;
import com.dgut.medicalsystem.entity.dto.MedicalStaffQuery;
import com.dgut.medicalsystem.entity.dto.PatientListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */

@Mapper
public interface MedicalStaffMapper extends BaseMapper<MedicalStaff> {

    @Select("select name from sys_role_user a,sys_role b where a.user_id= #{userId} and a.role_id=b.id")
    List<String> getRoleByUserId(@Param("userId") Integer id);

    /**
     * 根据医生id分页查找挂到该医生下的未结束看病流程的病人列表
     * @param id 医生id
     * @return 未结束流程病人列表
     */
    @Select("select * from user a,medical_history b where a.id=b.patient_id and b.doctor_id= #{id} and ISNULL(b.record_date)")
    List<User> selectPagePatientList(@Param("id")Integer id);

    /**
     * 查找所有缴费未取药的病人，并分页
     * @param userPage
     * @return
     */
    @Select("select c.* from medical_history a,bill b,user c where a.bill_id = b.id and b.bill_date is not null and a.record_date is null and a.patient_id = c.id")
    IPage<User> queryTakeMedicinList(Page<User> userPage);

    @Select("update medical_staff set deleted = #{deleted} where id = #{id} and id not in (select distinct doctor_id from medical_history where record_id is null)")
    void updateStatusById(@Param("id") Integer id,@Param("deleted") Integer deleted);


    @Select("SELECT a.* FROM medical_staff a, sys_role b, sys_role_user c WHERE " +
            "a.name like '%${medicalStaffQuery.name}%' and " +
            "a.subject like '%${medicalStaffQuery.subject}%' and " +
            "b.name_zh like '%${medicalStaffQuery.name_zh}%' and " +
            "(a.id = c.user_id and c.role_id = b.id)")
    IPage<MedicalStaff> queryMedicalStaff(Page<MedicalStaff> page,MedicalStaffQuery medicalStaffQuery);

    @Select("select id from medical_staff where name = '${name}'")
    int getIdByName(String name);

    @Update("UPDATE medical_staff SET subject = '${subject}' ," +
            "profection_title = '${ProfectionTitle}' ," +
            "room_no = '${roomNo}' where id = ${id}")
    int updateMedicalStaff(int id,String subject,String roomNo,String ProfectionTitle);

    @Select("select * from medical_staff")
    IPage<MedicalStaff> getAllMedicalStaffList(Page<MedicalStaff> medicalStaffPage);

    @Select("select * from availableDoctor_list where proTitle = '教授' limit 8")
    List<HotDoctor> selectHotDoctor();

    @Select("SELECT id,name,tel,subject FROM medical_staff where subject = #{departName} and username!='admin' ")
    List<MedicalStaff> selectByDepartment(@Param("departName")String departName);

    @Select("select role_id from sys_role_user where user_id = '${id}' ")
    int getRole_id(int id);

    @Select("select name_zh from sys_role where id = '${id}' ")
    String getName_zh(int id);
}

