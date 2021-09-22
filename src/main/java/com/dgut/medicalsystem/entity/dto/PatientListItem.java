package com.dgut.medicalsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 给医生用于展示病人列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PatientListItem implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 病人id
     */
    private Integer id;
    /**
     * 病人姓名
     */
    private String name;
    /**
     * 病人性别
     */
    private String sex;
    /**
     * 病人年龄
     */
    private Integer age;
    /**
     * 病人挂号时间
     */
    private String time;
    /**
     * 病人状态
     */
    private String status;
}
