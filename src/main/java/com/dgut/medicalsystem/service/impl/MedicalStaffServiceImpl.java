package com.dgut.medicalsystem.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.*;
import com.dgut.medicalsystem.entity.Record;
import com.dgut.medicalsystem.entity.dto.*;
import com.dgut.medicalsystem.mapper.*;
import com.dgut.medicalsystem.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dgut.medicalsystem.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class MedicalStaffServiceImpl extends ServiceImpl<MedicalStaffMapper, MedicalStaff> implements MedicalStaffService {

    @Autowired
    UserService userService;

    @Autowired
    RecordService recordService;

    @Autowired
    MedicalStaffMapper medicalStaffMapper;

    @Autowired
    MedicalHistoryService medicalHistoryService;

    @Autowired
    MedicineRecordMapper medicineRecordMapper;

    @Autowired
    BillMapper billMapper;

    @Autowired
    InformationMapper informationMapper;

    @Autowired
    AppointmentMapper appointmentMapper;

    @Autowired
    SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    SysRoleUserService sysRoleUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    MedicineService medicineService;

    @Override
    public boolean getByUsername(MedicalStaff user) {
        MedicalStaff medicalStaff = getOne(new LambdaQueryWrapper<MedicalStaff>()
                .eq(MedicalStaff::getUsername, user.getUsername()));
        return medicalStaff != null && medicalStaff.getPassword().equals(SaSecureUtil.md5(user.getPassword()));
    }

    @Override
    public String getRole(MedicalStaff user) {
        MedicalStaff medicalStaff = getOne(new LambdaQueryWrapper<MedicalStaff>()
                .eq(MedicalStaff::getUsername, user.getUsername()));
        //标记登录状态
        String s = "M" + medicalStaff.getId().toString();
        StpUtil.login(s);
        List<String> roles = medicalStaffMapper.getRoleByUserId(medicalStaff.getId());
        return roles.get(0);
    }

    @Override
    public boolean checkInfoById(Integer id) {
        MedicalStaff medicalStaff = getOne(new LambdaQueryWrapper<MedicalStaff>()
                .eq(MedicalStaff::getId,id));
        if (medicalStaff.getName() == null && medicalStaff.getBirth() == null &&
                medicalStaff.getSex() == null && medicalStaff.getIdno() == null && medicalStaff.getAddress() == null &&
                medicalStaff.getEmail() == null && medicalStaff.getTel() == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object getInfoById(Integer id) {
        MedicalStaff medicalStaff = getOne(new LambdaQueryWrapper<MedicalStaff>()
                .eq(MedicalStaff::getId, id));
        if (medicalStaff == null)
            return null;
        else
            medicalStaff.setPassword(null);
        return medicalStaff;
    }

    @Override
    public boolean updateInfomation(MedicalStaff user) {
        MedicalStaff updateUser = (MedicalStaff) this.getInfoById(user.getId());
        //更新姓名
        updateUser.setName(user.getName());

        //更新邮箱
        updateUser.setEmail(user.getEmail());

        //更新电话
        updateUser.setTel(user.getTel());

        //更新出生年月
        if (user.getBirth() != null) {
            updateUser.setAge((int) ChronoUnit.YEARS.between(user.getBirth(), LocalDate.now()));
        } else {
            updateUser.setAge(0);
        }
        updateUser.setBirth(user.getBirth());

        //更新性别
        updateUser.setSex(user.getSex());

        //更新身份证号
        updateUser.setIdno(user.getIdno());

        if (medicalStaffMapper.update(updateUser,new LambdaQueryWrapper<MedicalStaff>()
                .eq(MedicalStaff::getId,updateUser.getId())) == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<PatientListItem> doctorShowWaitForList(Integer id, Integer page, Integer size,String status) {
        List<PatientListItem> list = new ArrayList<>();
        List<User> userList = medicalStaffMapper.selectPagePatientList(id);
        int start = (page-1) * size;
        int count = -1;
        for (User user:userList) {
            if (getPatientStatus(user.getId()).equals(status)) {
                count++;
                if (count >= start) {
                    PatientListItem item = new PatientListItem();
                    item.setName(user.getName());
                    item.setAge(user.getAge());
                    item.setId(user.getId());
                    item.setSex(user.getSex());
                    item.setStatus(getPatientStatus(user.getId()));
                    item.setTime(getInformationTime(medicalHistoryService.getLatestHistory(user.getId()).getAppointmentId()));
                    list.add(item);
                    if (list.size() == size) {
                        break;
                    }
                }
            } else {
                continue;
            }
        }
        return list;
    }

    @Override
    public String getPatientStatus(Integer userId) {
        MedicalHistory medicalHistory = medicalHistoryService.getLatestHistory(userId);
        if (medicalHistory.getRecordId() == null) {
            return "未就诊";
        } else {
            List<MedicineRecord> medicineRecordList = medicineRecordMapper.getMedicineListByRecordId(medicalHistory.getRecordId());
            Bill bill = billMapper.selectOne(new LambdaQueryWrapper<Bill>()
                    .eq(Bill::getId, medicalHistory.getBillId()));
            if (medicineRecordList.size() == 0) {
                return "待开药";
            } else {
                if (bill.getBillDate() == null) {
                    return "待缴费";
                } else {
                    return "待取药";
                }
            }
        }
    }

    @Override
    public String getInformationTime(Long appointmentId) {
         Appointment appointment = appointmentMapper.selectOne(new LambdaQueryWrapper<Appointment>()
                .eq(Appointment::getId,appointmentId));
         return DateFormatUtil.dateFormat(appointment.getAppointmentDate());
    }


    @Override
    public List<String> getRoles(String user_id) {
        return medicalStaffMapper.getRoleByUserId(Integer.parseInt(user_id));
    }

    /**
     * 获取未取药的病人列表的所有信息，并封装到dto中的PatientListItem中 返回缴费未取药的user部分信息
     * @param page 当前页数
     * @param size 页面大小
     * @return
     */
    @Override
    public Map<String, Object> queryTakeMedicinList(int page, int size) {
        Page<User> userPage = new Page<>(page,size);
        List<PatientListItem> list = new ArrayList<>();
        IPage<User> iPage = medicalStaffMapper.queryTakeMedicinList(userPage);
        List<User> userList = iPage.getRecords();
        for (User user:userList) {
            PatientListItem item = new PatientListItem();
            item.setName(user.getName());
            item.setAge(user.getAge());
            item.setId(user.getId());
            item.setSex(user.getSex());
            item.setStatus(getPatientStatus(user.getId()));
            item.setTime(DateFormatUtil.dateFormat(billMapper.selectById(medicalHistoryService.getLatestHistory(user.getId()).getBillId()).getBillDate()));
            list.add(item);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", iPage.getTotal());
        return map;
    }

    /**
     * 查询药物单
     * @param userId
     * @return
     */
    @Override
    public MedicineRecord queryMedicine(Integer userId) {
        MedicineRecord medicineRecord = medicineRecordMapper.queryMedicineById(userId);
        return medicineRecord;
    }

    /**
     * 停用或启用医护人员账号
     *
     * @param id
     * @param deleted
     * @return
     */
    @Override
    public Boolean updateStatus(Integer id, Integer deleted) {
        try {
            medicalStaffMapper.updateStatusById(id, deleted);
            int result = 1;

            if (medicalStaffMapper.selectById(id) == null) {
                result = 0;
            }
            if (deleted == result) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public Map<String, Object> getPatientDetail(Integer id, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) userService.getInfoById(userId);
        map.put("userDetail",user);

        //用于存放展示到前端页面的病历数据的列表
        List<Map<String, Object>> dtoList = new ArrayList<>();

         //获取该病人所有病历记录,将病历条数放入展示map中
         IPage<MedicalHistory> historyIPageList = medicalHistoryService.getUserAllHistory(userId,1,3);
         List<MedicalHistory> historyList = historyIPageList.getRecords();
         if (historyList.size() == 0 || historyList == null) {
             map.put("historyCount",0);
         } else {
             map.put("historyCount",historyIPageList.getTotal());
         }

        //判断病人病历记录数，大于三条则展示三条
        if (historyList.size() == 0 || historyList == null) {
            return map;
        } else {
            if (historyList.size() < 3) {
                for (MedicalHistory medicalHistory : historyList) {
                    Record record = recordService.getOne(new LambdaQueryWrapper<Record>().eq(Record::getId,medicalHistory.getRecordId()));
                    MedicalStaff medicalStaff = getOne(new LambdaQueryWrapper<MedicalStaff>().eq(MedicalStaff::getId,medicalHistory.getDoctorId()));
                    Map<String, Object> recordmap = new HashMap<>();
                    recordmap.put("doctor", medicalStaff.getName());
                    recordmap.put("description",record.getCureDescription());
                    recordmap.put("date",DateFormatUtil.dateFormat(record.getRecordDate()));
                    dtoList.add(recordmap);
                }
            } else {
                for (int i=0; i<3; i++) {
                    MedicalHistory medicalHistory = historyList.get(i);
                    Record record = recordService.getOne(new LambdaQueryWrapper<Record>().eq(Record::getId,medicalHistory.getRecordId()));
                    MedicalStaff medicalStaff = getOne(new LambdaQueryWrapper<MedicalStaff>().eq(MedicalStaff::getId,medicalHistory.getDoctorId()));
                    Map<String, Object> recordmap = new HashMap<>();
                    recordmap.put("doctor", medicalStaff.getName());
                    recordmap.put("description",record.getCureDescription());
                    recordmap.put("date",DateFormatUtil.dateFormat(record.getRecordDate()));
                    dtoList.add(recordmap);
                }
            }
        }
        map.put("userHistory",dtoList);
        return map;
    }

    @Override
    public boolean insertDiagnose(Integer id, Integer userId, Record record) {
        LocalDateTime dateTime = LocalDateTime.now();
        Long recordId = id + userId + dateTime.toEpochSecond(ZoneOffset.of("+8"));

        MedicalHistory medicalHistory = medicalHistoryService.getOne(new LambdaQueryWrapper<MedicalHistory>().eq(MedicalHistory::getDoctorId,id).eq(MedicalHistory::getPatientId,userId).isNull(MedicalHistory::getRecordId));
        if (medicalHistory != null) {
            medicalHistory.setRecordId(recordId);
            medicalHistoryService.update(medicalHistory,new LambdaQueryWrapper<MedicalHistory>().eq(MedicalHistory::getId,medicalHistory.getId()));
            record.setRecordDate(dateTime);
            record.setCureStatus(2);
            record.setId(recordId);
            return recordService.save(record);
        } else {
            return false;
        }
    }

    /**
     * 查询药剂师、医生
     * @param medicalStaffQuery
     * @param page
     * @param size
     * @return
     */
    @Override

    public Map<String,Object> queryMedicalStaff(MedicalStaffQuery medicalStaffQuery, int page, int size){
        Map<String,Object> map = new HashMap<>();
        Page<MedicalStaff> Page = new Page<>(page,size);
        List<AdminCheckMedical_Staff> list = new ArrayList<>();

        IPage<MedicalStaff> iPage = medicalStaffMapper.queryMedicalStaff(Page,medicalStaffQuery);
        List<MedicalStaff> queryMedicalStaffList = iPage.getRecords();

        for (MedicalStaff medicalStaff:queryMedicalStaffList) {
            AdminCheckMedical_Staff item = new AdminCheckMedical_Staff();


            item.setId(medicalStaff.getId());
            //根据user_id查name_zh
            int role_id =  medicalStaffMapper.getRole_id(medicalStaff.getId());
            String name_zh = medicalStaffMapper.getName_zh(role_id);
            item.setName_zh(name_zh);
            item.setName(medicalStaff.getName());

            item.setAge(medicalStaff.getAge());
            item.setSex(medicalStaff.getSex());
            item.setAddress(medicalStaff.getAddress());

            item.setTel(medicalStaff.getTel());
            item.setSubject(medicalStaff.getSubject());
            item.setRoomNo(medicalStaff.getRoomNo());
            item.setProfectionTitle(medicalStaff.getProfectionTitle());
            list.add(item);
        }
        map.put("list",list);
        map.put("iPage",iPage);
        map.put("totla",iPage.getTotal());
        return map;
    }
    @Override
    @Transactional
    public boolean savemedicalStaff(UpdateMedicalStaff updatemedicalStaff) throws Exception{
        MedicalStaff medicalStaff = new MedicalStaff();

//        MedicalStaffService medicalStaffService = new MedicalStaffServiceImpl();
        /**
         * 存储medical_staff
         */
        medicalStaff.setName(updatemedicalStaff.getName());
        medicalStaff.setUsername(updatemedicalStaff.getUsername());
        medicalStaff.setPassword(updatemedicalStaff.getPassword());
        medicalStaff.setBirth(updatemedicalStaff.getBirth());
        medicalStaff.setAge(updatemedicalStaff.getAge());
        medicalStaff.setSex(updatemedicalStaff.getSex());
        medicalStaff.setIdno(updatemedicalStaff.getIdno());
        medicalStaff.setAddress(updatemedicalStaff.getAddress());
        medicalStaff.setEmail(updatemedicalStaff.getEmail());
        medicalStaff.setTel(updatemedicalStaff.getTel());
        medicalStaff.setSubject(updatemedicalStaff.getSubject());
        medicalStaff.setProfectionTitle(updatemedicalStaff.getProfectionTitle());
        medicalStaff.setRoomNo(updatemedicalStaff.getRoomNo());
        medicalStaff.setDeleted(updatemedicalStaff.getDeleted());
        medicalStaff.setAppointable(updatemedicalStaff.getAppointable());
        medicalStaff.setAvatar(updatemedicalStaff.getAvatar());

        save(medicalStaff);

        /**
         * 存储sys_role_user
         */
        int id = sysRoleService.getIdbyname_zh(updatemedicalStaff.getName_zh());
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setRoleId(id);
        sysRoleUser.setUserId(medicalStaffMapper.getIdByName(updatemedicalStaff.getName()));


        sysRoleUserService.save(sysRoleUser);
        System.out.println("SysRoleUser"+sysRoleUser);

        return true;
    }

    @Override
    public Integer getWaitForTotal(Integer id,String status) {
        List<User> userList = medicalStaffMapper.selectPagePatientList(id);
        int count = 0;
        for (User user:userList) {
            if (getPatientStatus(user.getId()).equals(status)) {
                count++;
            }
        }
        return count;
    }

    public int updateMedicalStaff(MedicalStaff medicalStaff){
        int flat = medicalStaffMapper.updateMedicalStaff(medicalStaff.getId(),medicalStaff.getSubject(),
                                                        medicalStaff.getRoomNo(),medicalStaff.getProfectionTitle());
        return flat;
    }

    @Override
    public List<AdminCheckMedical_Staff> queryTakeMedicalStaffList(int page, int size){
        Page<MedicalStaff> medicalStaffPage = new Page<>(page,size);
        List<AdminCheckMedical_Staff> list = new ArrayList<>();
        IPage<MedicalStaff> iPage = medicalStaffMapper.getAllMedicalStaffList(medicalStaffPage);
        List<MedicalStaff> medicalStaffList = iPage.getRecords();
        for (MedicalStaff medicalStaff:medicalStaffList) {
            AdminCheckMedical_Staff item = new AdminCheckMedical_Staff();
            item.setId(medicalStaff.getId());
            item.setName(medicalStaff.getName());

            item.setAge(medicalStaff.getAge());
            item.setSex(medicalStaff.getSex());
            item.setAddress(medicalStaff.getAddress());

            item.setTel(medicalStaff.getTel());
            item.setSubject(medicalStaff.getSubject());

            list.add(item);
        }
        return list;
    }

    @Override
    public MedicalStaff getByNewestAppointment(Integer uid) {
        MedicalHistory latestHistory = medicalHistoryService.getLatestHistory(uid);
        MedicalStaff staff = medicalStaffMapper.selectById(latestHistory.getDoctorId());
        staff.setUsername(null);
        staff.setPassword(null);
        staff.setBirth(null);
        staff.setAge(null);
        staff.setSex(null);
        staff.setIdno(null);
        staff.setAddress(null);
        staff.setTel(null);
        staff.setDeleted(null);
        staff.setAvatar(null);
        staff.setEmail(null);
        return staff;
    }

    @Override
    public MedicalStaff getNeccessaryInfo(Integer Id) {
        MedicalStaff staff = medicalStaffMapper.selectById(Id);
        staff.setIdno(null);
        staff.setBirth(null);
        staff.setUsername(null);
        staff.setDeleted(null);
        staff.setPassword(null);
        staff.setAddress(null);
        return staff;
    }


    @Override
    public List<HotDoctor> getHotDoctor() {
        return medicalStaffMapper.selectHotDoctor();
    }

    @Override
    public List<MedicalStaff> getByDepartment(String departName) {
        List<MedicalStaff> staffs = medicalStaffMapper.selectByDepartment(departName);
        return staffs;
    }

    @Override
    public boolean submitPrescription(Integer userId, List<MedicineRecord> list) {
        Long recordId = medicalHistoryService.getLatestHistory(userId).getRecordId();
        try {
            for (MedicineRecord record : list) {
                Medicine medicine = medicineService.getById(record.getMedicineId());
                if (medicine.getMedicineStock()<record.getMedicineNumber()) {
                    return false;
                } else {
                    medicine.setMedicineStock(medicine.getMedicineStock() - record.getMedicineNumber());
                    medicineService.update(medicine,new LambdaQueryWrapper<Medicine>().eq(Medicine::getId,medicine.getId()));
                    record.setRecordId(recordId);
                    medicineRecordMapper.insert(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}


