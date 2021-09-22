package com.dgut.medicalsystem.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.MedicalHistory;
import com.dgut.medicalsystem.entity.User;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.mapper.UserMapper;
import com.dgut.medicalsystem.service.MedicalHistoryService;
import com.dgut.medicalsystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.dgut.medicalsystem.entity.dto.Progress;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MedicalHistoryService medicalHistoryService;


    @Override
    public User getByUsername(User login_user) {
        User res_user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, login_user.getUsername()));
        if (res_user != null && res_user.getPassword().equals(SaSecureUtil.md5(login_user.getPassword()))) {
            //标记登录状态
            String s = "P" + res_user.getId().toString();
            if (!StpUtil.isLogin()) {
                StpUtil.login(s);
            }
            return res_user;
        } else return null;
    }

    @Override
    public boolean checkInfoById(Integer id) {
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, id));
        return user.getName() != null || user.getBirth() != null ||
                user.getSex() != null || user.getIdno() != null || user.getAddress() != null ||
                user.getEmail() != null || user.getTel() != null || user.getIsmarid() != null || user.getNation() != null ||
                user.getBloodtype() != null || user.getCountry() != null || user.getAllergicHistory() != null;
    }

    @Override
    public Object getInfoById(Integer id) {
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, id));
        user.setPassword(null);
        return user;
    }

    @Override
    public boolean updateInfomation(User user) {
        User updateUser = (User) this.getInfoById(user.getId());
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

        //更新地址
        updateUser.setAddress(user.getAddress());

        //更新血型
        updateUser.setBloodtype(user.getBloodtype());

        //更新是否已婚
        updateUser.setIsmarid(user.getIsmarid());

        //更新国籍
        updateUser.setCountry(user.getCountry());

        //更新民族
        updateUser.setNation(user.getNation());

        //更新过敏史
        updateUser.setAllergicHistory(user.getAllergicHistory());

        if (userMapper.update(updateUser, new LambdaQueryWrapper<User>()
                .eq(User::getId, updateUser.getId())) == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Progress checkProgressById(Integer id) {
        Progress progress = userMapper.checkProgressById(id);
        if (progress == null) {
            return null;
        }
        if (progress.getAppointTime() != null) {
            progress.setAppointStatus("挂号已完成");
        }
        if (progress.getCureTime() != null) {
            progress.setCureStatus("诊断完成");
        } else if (progress.getAppointStatus().equals("挂号已完成")) {
            progress.setCureStatus("等待叫号中");
            int docid = medicalHistoryService.getOne(new LambdaQueryWrapper<MedicalHistory>()
                    .eq(MedicalHistory::getPatientId, id)
                    .isNull(MedicalHistory::getRecordDate)).getDoctorId();
            System.out.println(docid);
            progress.setQueueNumber(userMapper.getQueueNumber(docid));
        }
        if (progress.getBillTime() != null) {
            progress.setBillStatus("缴费已完成");
        } else {
            progress.setBillStatus("待缴费");
        }
        if (progress.getFinishTime() != null) {
            progress.setFinishStatus("治疗已完成");
        } else {
            progress.setFinishStatus("医疗未完成");
        }
        return progress;
    }

    @Override
    public boolean checkUserById(Integer userId) {
        User user = getOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, userId));
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkPass(User user){
        int check = count(new QueryWrapper<User>()
        .eq("id",user.getId()).eq("password",user.getPassword()));
        return check != 0;
    }

    @Override
    public Integer updatePass(User user) {
        boolean flag = update(new UpdateWrapper<User>()
        .eq("id",user.getId()).set("password",user.getPassword()));
        if(flag)
            return 1;
        return 0;
    }

    @Override
    public Boolean updateIdNo(User user) {
        return update(new UpdateWrapper<User>()
                .eq("id", user.getId())
                .set("idno", user.getIdno()));
    }

}
