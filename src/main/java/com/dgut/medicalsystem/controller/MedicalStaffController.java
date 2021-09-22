package com.dgut.medicalsystem.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.*;

import com.dgut.medicalsystem.entity.Record;

import com.dgut.medicalsystem.entity.dto.*;
import com.dgut.medicalsystem.service.*;
import com.dgut.medicalsystem.service.impl.MedicalStaffServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Slf4j
@RestController
@RequestMapping("/medical-staff")
public class MedicalStaffController {

    @Autowired
    MedicalStaffService medicalStaffService;

    @Autowired
    MedicineService medicineService;

    @Autowired
    MedicalHistoryService medicalHistoryService;

    @Autowired
    BillService billService;

    @Autowired
    SysRoleUserService sysRoleUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    RecordService recordService;

    @Autowired
    UserService userService;

    @RequestMapping("login")
    public Map<String, String> login(@RequestBody MedicalStaff user) {
        Map<String, String> map = new HashMap<>();
        if (user.getPassword().isBlank() || user.getUsername().isBlank()) {
            map.put("ret", "2");
            map.put("description", "数据非法");
            return map;
        }
        try {
            //判断登录是否成功
            boolean res = medicalStaffService.getByUsername(user);
            if (res) {
                //获取该用户角色
                map.put("roles", medicalStaffService.getRole(user));
                map.put("ret", "0");
                map.put("username", user.getUsername());
                map.put("description", "登录成功");
                map.put("tokenName", StpUtil.getTokenInfo().getTokenName());
                map.put("tokenValue", StpUtil.getTokenInfo().getTokenValue());
            } else {
                map.put("ret", "1");
                map.put("description", "帐号或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    @SaCheckLogin
    @RequestMapping("getInformation")
    public Map<String, Object> doctorGetInformation() {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        try {
            //判断个人信息是否为空
            boolean res = medicalStaffService.checkInfoById(id);
            if (res) {
                map.put("ret", "0");
                map.put("description", "查找成功");
                map.put("userInfo", medicalStaffService.getInfoById(id));
            } else {
                map.put("ret", "1");
                map.put("description", "该用户未填写个人信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("ret", "3");
            map.put("description", "系统错误");
        }
        return map;
    }

    @SaCheckLogin
    @RequestMapping("updateInformation")
    public Map<String, Object> updateInformation(@RequestBody MedicalStaff user) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        user.setId(id);
        try {
            if (medicalStaffService.updateInfomation(user)) {
                map.put("ret", "0");
                map.put("description", "更新成功");
            } else {
                map.put("ret", "1");
                map.put("description", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @SaCheckLogin
    @RequestMapping("addInformation")
    public Map<String, Object> addInformation(@RequestBody MedicalStaff user) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        user.setId(id);
        try {
            if (medicalStaffService.updateInfomation(user)) {
                map.put("ret", "0");
                map.put("description", "添加成功");
            } else {
                map.put("ret", "1");
                map.put("description", "添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    @SaCheckLogin
    @SaCheckRole("doctor")
    @PostMapping("/doctor/submitPrescription")
    public Map<String, Object> doctorSubmitPrescription(@RequestBody Map<String, Object> data) {
        //Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        Map<String, Object> map = new HashMap<>();
        Object obj = data.get("list");
        String st = JSONArray.toJSONString(obj);
        List<MedicineRecord> list = JSONObject.parseArray(st,MedicineRecord.class);
        Integer userId = Integer.parseInt(data.get("id").toString());
        if (medicalStaffService.submitPrescription(userId,list)) {
            map.put("ret",0);
            map.put("description","提交成功");
        } else {
            map.put("ret",1);
            map.put("description","提交失败");
        }
        return map;
    }

    @SaCheckLogin
    @SaCheckRole("doctor")
    @GetMapping("/doctor/showWaitForOpenList")
    public Map<String, Object> doctorShowWaitForOpenList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        List<PatientListItem> list = medicalStaffService.doctorShowWaitForList(id, page, size,"待开药");
        if (list != null) {
            map.put("ret", "0");
            map.put("description", "待开药列表获取成功！");
            map.put("list", list);
            map.put("total",medicalStaffService.getWaitForTotal(id,"待开药"));
        } else {
            map.put("ret", "1");
            map.put("description", "待开药列表获取失败或该医生待开药信息");
        }
        return map;
    }

    @SaCheckLogin
    @SaCheckRole("doctor")
    @RequestMapping("/doctor/showWaitForSeeList")
    public Map<String, Object> doctorShowWaitForSeeList(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        List<PatientListItem> list = medicalStaffService.doctorShowWaitForList(id, page, size,"未就诊");
        if (list != null) {
            map.put("ret", "0");
            map.put("description", "挂号列表获取成功！");
            map.put("list", list);
            map.put("total",medicalStaffService.getWaitForTotal(id,"未就诊"));
        } else {
            map.put("ret", "1");
            map.put("description", "挂号列表获取失败或该医生暂无挂号信息");
        }
        return map;
    }


    @SaCheckLogin
    @SaCheckRole("doctor")
    @PostMapping("/doctor/getMedicineList")
    public Map<String, Object> doctorGetMedicineList(@RequestBody Map<String,String> data) {
        Map<String, Object> map = new HashMap<>();
        Integer page = Integer.valueOf(data.get("page"));
        Integer size = Integer.valueOf(data.get("size"));
        Page<Medicine> medicinePage = new Page<>(page,size);
        MedicineQuery medicineQuery = new MedicineQuery();
        if (data.containsKey("medicineName")) {
            medicineQuery.setMedicineName(data.get("medicineName"));
        } else {
            medicineQuery.setMedicineName("");
        }

        if (data.containsKey("medicalType")) {
            medicineQuery.setMedicalType(data.get("medicalType"));
        } else {
            medicineQuery.setMedicalType("");
        }

        medicineQuery.setMedicineStatus(1);

        IPage<Medicine> medicineIPage = medicineService.queryDrugInventory(medicinePage,medicineQuery);
        if (medicineIPage.getTotal() != 0) {
            map.put("ret",0);
            map.put("description","药物列表获取成功");
            map.put("list",medicineIPage.getRecords());
            map.put("total",medicineIPage.getTotal());
        } else {
            map.put("ret", "1");
            map.put("description", "药物列表获取失败");
        }
        return map;
    }
    /**
     * 医生开始诊断病人，展示病人基本信息
     **/
    @SaCheckLogin
    @SaCheckRole("doctor")
    @PostMapping("/doctor/startDiagnose")
    public Map<String,Object> startDiagnose(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        Map<String, Object> getmap = medicalStaffService.getPatientDetail(id,user.getId());
        if (getmap != null) {
            map.put("ret","0");
            map.put("description", "获取成功！");
            map.put("userDetail",getmap.get("userDetail"));
            map.put("historyCount",getmap.get("historyCount"));
            if (getmap.get("userHistory") != null) {
                map.put("userHistory",getmap.get("userHistory"));
            } else {
                map.put("userHistory",null);
            }
        } else {
            map.put("ret","1");
            map.put("description", "查无此人信息");
        }
        return map;
    }


    /**
     * 查看病人所有诊断记录
     */
    @SaCheckLogin
    @SaCheckRole("doctor")
    @GetMapping("/doctor/getUserTotalRecord")
    public Map<String, Object> showTotalHistory(@RequestParam("userId") Integer userId,@RequestParam("page") Integer page,@RequestParam("size")Integer size){
        Map<String, Object> map = new HashMap();
        if (userService.checkUserById(userId) == false) {
            map.put("ret",2);
            map.put("description","查无此人");
            return map;
        }
        List<Map<String, Object>> records = recordService.getUserAllRecord(userId,page,size);
        if (records.isEmpty()) {
            map.put("ret",1);
            map.put("description","该用户无既往病史");
        } else {
            map.put("ret",0);
            map.put("description","查找成功");
            map.put("list",records);
            map.put("count",recordService.getUserAllRecordCount(userId));
        }
        return map;
    }

    @SaCheckLogin
    @SaCheckRole("doctor")
    @PostMapping("/doctor/submitDiagnose")
    public Map<String, Object> submitDiagnose(@RequestBody Map<String, String> data) {
        Map<String, Object> map = new HashMap<>();
        Integer userId = Integer.parseInt(data.get("userId"));
        Record record = new Record();
        record.setCureDescription(data.get("description"));
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        if (medicalStaffService.insertDiagnose(id,userId,record)) {
            map.put("ret","0");
            map.put("description","提交成功");
        } else {
            map.put("ret","1");
            map.put("description","提交失败");
        }
        return map;
    }
    /**
     * 药剂师增加药物
     */
    @SaCheckLogin
    @SaCheckRole("pharmacist")
    @PostMapping("pharmacist/addMedicine")
    public Map<String, Object> addMedicine(@RequestBody Medicine medicine) {
        Map<String, Object> map = new HashMap<>();

        if (medicine.getMedicalType().isEmpty() || medicine.getMedicineName().isEmpty() || medicine.getMedicineOriginalPrice() == null ||
                medicine.getMedicinePrice() <= 0.0 || medicine.getMedicineStatus() > 1 || medicine.getMedicineStatus() < 0 || medicine.getMedicineStock() <= 0 ||
                medicine.getSpecifications().isEmpty()
        ) {
            map.put("ret", "1");
            map.put("description", "药物信息不完整");
        } else {
            map.put("ret", "0");
            boolean save = medicineService.save(medicine);
            if (save) {
                map.put("description", "药物添加成功");
            } else {
                map.put("ret", "1");
                map.put("description", "药物信息非法");
            }
        }
        return map;
    }

    /**
     * 修改药物信息
     */
    @SaCheckRole(value = "pharmacist")
    @PutMapping("pharmacist/updateMedicine")
    public Map<String, Object> updateMedicine(@RequestBody Medicine medicine) {
        Map<String, Object> map = new HashMap<>();

        if (medicine.getMedicalType().isEmpty() || medicine.getMedicineName().isEmpty() || medicine.getMedicineOriginalPrice() == null ||
                medicine.getMedicinePrice() <= 0.0 || medicine.getMedicineStatus() > 1 || medicine.getMedicineStatus() < 0 || medicine.getMedicineStock() <= 0 ||
                medicine.getSpecifications().isEmpty()
        ) {
            map.put("ret", "1");
            map.put("description", "药物信息不完整,不能保存");
        } else {

        if (medicine.getMedicineStatus() == 0) {
                //修改逻辑删除字段medicinestatus的状态
                medicineService.updateStatus(medicine.getId());
            }
            medicineService.updateById(medicine);

            map.put("ret", "0");
            map.put("description", "药物信息修改成功");
        }
        return map;
    }


    /**
     * 确认病人取药
     */
    @SaCheckRole("pharmacist")
    @RequestMapping("pharmacist/comfireTaking")
    public Map<String, Object> finishConfirmTaking(@RequestParam("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        MedicalHistory one = medicalHistoryService.getOne(new QueryWrapper<MedicalHistory>()
                .eq("patient_id", id)
        );
        Integer mh_id = one.getId();

        //获取该id对应的病历表
        MedicalHistory medicalHistory = medicalHistoryService.getById(mh_id);
        //确认取药时，前面所有步骤都应该做完了
        if (medicalHistory.getRecordId() == null) {
            map.put("res", "1");
            map.put("description", "病人没有进行诊断");
        } else if (medicalHistory.getBillId()==null) {
            map.put("res", "1");
            map.put("description", "账单丢失");
        } else {
            Bill bill = billService.getById(medicalHistory.getBillId());
            if (bill.getBillDate() == null) {
                map.put("res", "1");
                map.put("description", "病人未缴费");
            }
            if (medicalHistory.getRecordDate() == null) {
                //获取当前时间
                LocalDateTime localDateTime = LocalDateTime.now();
                medicalHistory.setRecordDate(localDateTime);
                medicalHistoryService.updateById(medicalHistory);
                map.put("res", "0");
                map.put("description", "取药成功");
            } else {
                map.put("res", "1");
                map.put("description", "病人已缴费");
            }
        }
        return map;
    }

    /**
     * 查看药物库存
     */
    @SaCheckRole(value = {"pharmacist"})
    @PostMapping("pharmacist/queryDrugInventory")
    public Map<String, Object> queryDrugInventory(@RequestBody MedicineQuery medicineQuery) {
        Map<String, Object> map = new HashMap<>();
        /*当前页数和每页大小*/
        //创建分页对象，每页多少条，当前页码
        Page<Medicine> page = new Page<>(medicineQuery.getPage(), medicineQuery.getSize());
        IPage<Medicine> medicineIPage = medicineService.queryDrugInventory(page, medicineQuery);
        map.put("ret", 0);
        map.put("description", "查询成功");
        map.put("medicineIPage", medicineIPage);
        return map;
    }

    /**
     * 停用药物（药物库存中的药物）
     */
    @SaCheckRole("pharmacist")
    @RequestMapping("pharmacist/stopmedicine")
    public Map<String, Object> stopmedicine(@RequestParam("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Boolean flag = medicineService.updateStatus(id);
        if (flag) {
            map.put("ret", "0");
            map.put("description", "药物停用成功");
        } else {
            map.put("ret", "1");
            map.put("description", "药物停用失败，找不到该药物");
        }

        return map;
    }

    /**
     * 查看待取药病人列表（所有交完药物费用之后才会显示在这里）
     */
    @SaCheckRole("pharmacist")
    @RequestMapping("pharmacist/takeMedicinList")
    public Map<String,Object> takeMedicinList(@RequestBody Pagination pagination) {
        Map<String, Object> map = new HashMap<>();
        //获取当前页码和每页大小
        int page = pagination.getPage();
        int size = pagination.getSize();
        Map<String, Object> queryTakeMedicinList = medicalStaffService.queryTakeMedicinList(page, size);
        if (queryTakeMedicinList != null) {
            map.put("patientListItems", queryTakeMedicinList);
            map.put("ret", 0);
            map.put("description", "查找未取药人员成功");
        }else {
            map.put("ret", "1");
            map.put("description", "无人员");
        }
        return map;
    }

    /**
     * 取药单查询()
     */
    @SaCheckRole("pharmacist")
    @RequestMapping("pharmacist/queryMedicine")
    public Map<String,Object> queryMedicine(@RequestParam("userId")Integer userId){
        Map<String, Object> map = new HashMap<>();

        System.out.println("===============================");
        MedicineRecord medicineRecord = medicalStaffService.queryMedicine(userId);
        map.put("medicineRecord", medicineRecord);
        map.put("ret", 0);
        map.put("description", "查询诊断药物成功");
        return map;
    }

    /**
     * 停用医护人员账号
     */
    @SaCheckRole("superadmin")
    @RequestMapping("accountStatus")
    public Map<String, Object> accountStatus(@RequestBody MedicalStaff medicalStaff) {
        Map<String, Object> map = new HashMap<>();
        Boolean flag = medicalStaffService.updateStatus(medicalStaff.getId(), medicalStaff.getDeleted());
        if (flag) {
            map.put("ret", "0");
            map.put("description", "账号状态修改成功");
        } else {
            map.put("ret", "1");
            map.put("description", "账号状态修改失败");
        }
        return map;
    }
    /**
     * 医生、前台、药剂师账户查询
     */
    @SaCheckRole("superadmin")
    @RequestMapping("/queryMedicalStaff")
    public Map<String, Object> query(@RequestBody Map<String,String> data){
        Map<String, Object> map = new HashMap<>();
        int page = Integer.parseInt(data.get("page"));
        int size = Integer.parseInt(data.get("size"));
        MedicalStaffQuery medicalStaffQuery = new MedicalStaffQuery();
        medicalStaffQuery.setName(data.get("name"));
        medicalStaffQuery.setSubject(data.get("subject"));
        medicalStaffQuery.setName_zh(data.get("name_zh"));
        Map<String,Object> map1 =  medicalStaffService.queryMedicalStaff(medicalStaffQuery,page,size);

        if (!map1.get("list").equals(null)) {
            map.put("ret", "0");
            map.put("description", "查询成功！");
            map.put("ipage",map1.get("iPage"));
            System.out.println();
        } else {
            map.put("ret", "1");
            map.put("description", "无查询结果");
        }
        return map;
    }

    /**
     * 医生、前台、药剂师账户添加
     */
    @RequestMapping("/addMedicalStaff")
    public Map<String, Object>addMedicaStaff(@RequestBody UpdateMedicalStaff updatemedicalStaff) {
        Map<String, Object> map = new HashMap<>();

        if(updatemedicalStaff.getUsername().isEmpty()||updatemedicalStaff.getPassword().isEmpty())
             {
                map.put("ret", "1");
                map.put("description", "用户信息不合法");
            }

        try {
            medicalStaffService.savemedicalStaff(updatemedicalStaff);
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("ret", "0");
        map.put("description", "添加成功");

        return map;
    }

    /**
     * 修改药剂师、医生、前台管理员
     * @param medicalStaff
     * @return
     */
    @RequestMapping("updateMedicalStaff")
    public Map<String, Object> upDateMedicalStaff(@RequestBody MedicalStaff medicalStaff){

        Map<String, Object> map = new HashMap<>();

        int flat = medicalStaffService.updateMedicalStaff(medicalStaff);
        if (flat!=0) {
            map.put("ret", "0");
            map.put("description", "账户信息修改成功");

        }
        else {
            map.put("ret","1");
            map.put("description", "账户信息修改失败");

        }
        return map;
    }

    /**
     * 管理员主界面查看医护人员信息onclick
     */
    @SaCheckRole("superadmin")
    @RequestMapping("onclickMedicalStaff")
    public Map<String, Object> getAllMedicalStaff(@RequestBody Pagination pagination){
        Map<String, Object> map = new HashMap<>();
        //获取当前页码和每页大小
        int page = pagination.getPage();
        int size = pagination.getSize();
        List<AdminCheckMedical_Staff> medicalStaffListItems = medicalStaffService.queryTakeMedicalStaffList(page, size);
        if (!medicalStaffListItems.isEmpty()) {
            map.put("medicalStaffListItems", medicalStaffListItems);

            map.put("ret", 0);
            map.put("description", "查找查看医护人员成功");
            map.put("total",medicalStaffListItems.size());

        }else {
            map.put("ret", "1");
            map.put("description", "无医护人员");
        }
        return map;
    }

    /**
     * 点击修改按钮时数据回显
     * @param adminCheckMedical_staff
     * @return
     */
    @SaCheckRole("superadmin")
    @RequestMapping("onclickUpdateMedicalStaff")
    public Map<String, Object> onclickUpdateMedicalStaff(@RequestBody AdminCheckMedical_Staff adminCheckMedical_staff){
        Map<String, Object> map = new HashMap<>();
        int id = adminCheckMedical_staff.getId();

        MedicalStaff medicalStaff = medicalStaffService.getById(id);


        map.put("ret", "0");
        map.put("description", "医护人员信息");
        map.put("medicalStaff",medicalStaff);


        return map;
    }

    @RequestMapping("/hotDoctor")
    public Map<String,Object> getHotDoctor(){
        Map<String,Object> map = new HashMap<>();
        try{
            List<HotDoctor> hotDoctor = medicalStaffService.getHotDoctor();
            map.put("ret",0);
            map.put("msg","获取成功");
            map.put("data",hotDoctor);
        }
        catch (Exception e){
            map.put("ret",1);
            map.put("msg","获取失败");
            map.put("data",null);
        }
        return map;
    }

    @RequestMapping("/getDoctorInfo")
    public Map<String,Object> getDoctorInfo(@RequestParam(value = "docId",required = true) Integer docId){
        Map<String,Object> map = new HashMap<>();
        try{
            MedicalStaff staff = (MedicalStaff) medicalStaffService.getInfoById(docId);
            if (staff != null){
                staff.setIdno(null);
                map.put("ret",0);
                map.put("msg","获取成功");
                map.put("data",staff);
            }
            else{
                map.put("ret",2);
                map.put("msg","暂无数据");
                map.put("data",null);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            map.put("ret",1);
            map.put("msg","获取失败");
            map.put("data",null);
        }
        return map;
    }

}
