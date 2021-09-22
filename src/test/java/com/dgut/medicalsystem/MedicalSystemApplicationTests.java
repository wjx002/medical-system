package com.dgut.medicalsystem;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.controller.MedicalStaffController;
import com.dgut.medicalsystem.entity.Bill;
import com.dgut.medicalsystem.entity.MedicalStaff;
import com.dgut.medicalsystem.entity.MedicineRecord;
import com.dgut.medicalsystem.entity.SysRoleUser;
import com.dgut.medicalsystem.entity.User;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.entity.dto.Pagination;
import com.dgut.medicalsystem.entity.dto.UpdateMedicalStaff;
import com.dgut.medicalsystem.mapper.MedicalStaffMapper;
import com.dgut.medicalsystem.mapper.MedicineRecordMapper;
import com.dgut.medicalsystem.mapper.UserMapper;
import com.dgut.medicalsystem.service.BillService;
import com.dgut.medicalsystem.service.SysRoleService;
import com.dgut.medicalsystem.service.SysRoleUserService;
import com.dgut.medicalsystem.service.impl.SysRoleServiceImpl;
import com.dgut.medicalsystem.service.impl.SysRoleUserServiceImpl;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;


@SpringBootTest
class MedicalSystemApplicationTests {

    @Autowired
    MedicalStaffMapper mapper;

    @Autowired
    MedicineRecordMapper recordMapper;



    @Autowired
    BillService billService;

//    @Autowired
//    MedicalStaffController medicalStaffController;

    @Test
    void contextLoads() {
        Page<User> userPage = new Page<>(1, 2);
        IPage<User> iPage = mapper.queryTakeMedicinList(userPage);
//        Page<String> userPage = new Page<>(1,2);
//        IPage<String> iPage = mapper.queryTakeMedicinList(userPage);
        System.out.println(iPage.getPages());
        System.out.println(iPage.getTotal());
        System.out.println(iPage.getRecords());
    }
    @Test
    void jiaxutest(){
        MedicineRecord medicineRecord = recordMapper.queryMedicineById(3);
        System.out.println(medicineRecord);
//        mapper.updateStatusById(7, 0);
//        Integer deleted = mapper.selectById(7).getDeleted();
//        System.out.println(deleted);
//        MedicalStaff medicalStaff = mapper.selectById(7);
//        System.out.println(medicalStaff)

    }
    @Test
    void test(){
        MedicalStaff medicalStaff = new MedicalStaff();

        SysRoleUserService sysRoleUserService = new SysRoleUserServiceImpl();

        SysRoleService sysRoleService = new SysRoleServiceImpl();

        MedicalStaff medicalStaff1 = new MedicalStaff();
       UpdateMedicalStaff updatemedicalStaff = new UpdateMedicalStaff();
//        MedicalStaffService medicalStaffService = new MedicalStaffServiceImpl();

    }


    @Test
    void BillTest() {
        Pagination pagination = new Pagination(1, 8);
        Page<Bill> page = new Page<>(pagination.getPage(), pagination.getSize());
        billService.getBillListById(6, page);
    }

    @Test
    void DateTest(){
        System.out.println();
    }

}
