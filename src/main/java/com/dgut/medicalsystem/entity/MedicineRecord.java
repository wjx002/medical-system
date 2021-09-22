package com.dgut.medicalsystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 药物列表
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MedicineRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 诊断记录ID
     */
    private Long recordId;

    /**
     * 药品ID
     */
    private Integer medicineId;

    /**
     * 药品名
     */
    private String medicineName;

    /**
     * 药物类型 中药/西药/检查项目 
     */
    private String medicineType;

    /**
     * 药物数量
     */
    private Integer medicineNumber;

    /**
     * 药物价格
     */
    private Double medicinePrice;

    /**
     * 药物规格
     */
    private String specifications;

    /**
     * 用法
     */
    private String usaged;

    /**
     * 医嘱
     */
    private String advice;


}
