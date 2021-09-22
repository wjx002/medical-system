package com.dgut.medicalsystem.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.*;

/**
 *
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UpdateMedicalStaff {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码(非明文存储)
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 出生年月
     */
    private LocalDate birth;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idno;

    /**
     * 居住地址
     */
    private String address;


    /**
     * 电话
     */
    private String tel;


    /**
     * 所属科系
     */
    private String subject;

    /**
     * 职称  对应挂号费不一样
     */
    private String profectionTitle;

    /**
     * 所属看诊室号
     */
    private String roomNo;

    /**
     * 是否可挂号     1---可挂号   0---不可挂号
     */
    private Integer appointable;

    /**
     * 是否停用 1--启用 0--未启用
     */
    @TableLogic
    private Integer deleted;

    /**
     * 头像
     */
    private String avatar;


    /**
     * 邮箱
     */
    private String email;

    private String name_zh;


}
