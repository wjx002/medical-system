package com.dgut.medicalsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.entity.dto.Progress;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface UserService extends IService<User> {
    User getByUsername(User user);

    /**
     * 通过Id判断病人是否填写了个人基本信息
     */
    boolean checkInfoById(Integer id);

    /**
     * 通过Id获取病人的个人基本信息
     *
     * @return
     */
    Object getInfoById(Integer id);

    /**
     * 更新病人个人基本信息
     */
    boolean updateInfomation(User user);

    /**
     * 查询进度状态
     */
    Progress checkProgressById(Integer id);

    boolean checkUserById(Integer userId);

    /*
     * 校验密码
     * */
    boolean checkPass(User user);

    /*
     * 更新密码
     * */
    Integer updatePass(User user);

    /*
    * 更新身份证
    * */
    Boolean updateIdNo(User user);

}
