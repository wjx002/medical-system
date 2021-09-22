package com.dgut.medicalsystem.service;

import com.dgut.medicalsystem.entity.MedicalStaff;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dgut.medicalsystem.entity.MedicineRecord;
import com.dgut.medicalsystem.entity.Record;
import com.dgut.medicalsystem.entity.dto.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface MedicalStaffService extends IService<MedicalStaff> {


    /**
     * 通过用户名密码获取账户信息
     */
    boolean getByUsername(MedicalStaff medicalStaff);

    /**
     * 通过用户名（对象）查询身份
     */
    String getRole(MedicalStaff user);

    /**
     * 通过ID获取身份列表
     */
    List<String> getRoles(String user_id);

    /**
     * 通过Id判断职工是否填写了个人基本信息
     */
    boolean checkInfoById(Integer id);

    /**
     * 通过Id获取职工的个人基本信息
     */
    Object getInfoById(Integer id);

    /**
     * 更新职工个人基本信息
     */
    boolean updateInfomation(MedicalStaff user);

    /**
     * 通过医生Id获取挂号到该医生下的病人列表
     */
    List<PatientListItem> doctorShowWaitForList(Integer id,Integer page,Integer size,String status);

    /**
     * 通过病人Id获取病人当前状态
     * @param userId 病人id
     * @return 病人当前状态(未就诊,待开药,待缴费,待取药)
     */
    String getPatientStatus(Integer userId);

    /**
     * 通过病历Id获取病人挂号时间
     *
     * @param InfoId 病历id
     * @return 挂号时间的字符串形式
     */
    String getInformationTime(Long InfoId);



//    IPage<String> queryTakeMedicinList(Page<String> stringPage);

    /**
     * 获取缴费未取药的病人列表
     * @param page
     * @param size
     * @return
     */
    Map<String,Object> queryTakeMedicinList(int page, int size);

    /**
     * 查询药物单
     * @param userId
     * @return
     */
    MedicineRecord queryMedicine(Integer userId);

    /**
     * 停用或启用医护人员账号
     * @param id
     * @param deleted
     * @return
     */
    Boolean updateStatus(Integer id, Integer deleted);

    /**
     * 获取病人详细信息及过往病史
     * @param id 医生Id
     * @param userId 病人Id
     * @return userDetail病人详细信息   userHistory病人过往病史  historyCount病人过往病历数
     */
    Map<String, Object> getPatientDetail(Integer id, Integer userId);

    /**
     * 添加医生诊断记录
     * */
    boolean insertDiagnose(Integer id, Integer userId, Record record);


    /**
     * 医生、前台、药剂师账户查询
     * @param medicalStaffQuery
     * @param page
     * @param size
     * @return
     */
    Map<String,Object> queryMedicalStaff(MedicalStaffQuery medicalStaffQuery, int page, int size);

    public boolean savemedicalStaff(UpdateMedicalStaff updatemedicalStaff) throws Exception;

    /**
     * 获取医生下未就诊人数
     * */
    Integer getWaitForTotal(Integer id,String status);

    /**
     * 修改医生、药剂师、前台信息(subject,profection,room_no)
     * @param medicalStaff
     * @return
     */
    public int updateMedicalStaff(MedicalStaff medicalStaff);

    List<AdminCheckMedical_Staff> queryTakeMedicalStaffList (int page, int size);

    MedicalStaff getByNewestAppointment(Integer id);

    MedicalStaff getNeccessaryInfo(Integer Id);

    List<HotDoctor> getHotDoctor();

    List<MedicalStaff> getByDepartment(String departName);

    boolean submitPrescription(Integer userId, List<MedicineRecord> list);
}


