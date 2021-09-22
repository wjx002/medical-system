package com.dgut.medicalsystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 科室表

 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 科室编码
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 科室名称
     */
    private String name;

    /**
     * 科室描述
     */
    private String description;

    /**
     * 科室是否启用 1---启用  0---未启用
     */
    private Integer type;

    private String pics;


}
