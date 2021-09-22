package com.dgut.medicalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码（非明文存储）
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
     * 身份证号码
     */
    private String idno;

    /**
     * 居住地址
     */
    private String address;

    /**
     * 血型
     */
    private String bloodtype;

    /**
     * 电话
     */
    private String tel;

    /**
     * 民族
     */
    private String nation;

    /**
     * 是否已婚
     */
    private Integer ismarid;

    /**
     * 国籍
     */
    private String country;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 过敏史
     */
    private String allergicHistory;

    /**
     * 邮箱
     */
    private String email;


}
