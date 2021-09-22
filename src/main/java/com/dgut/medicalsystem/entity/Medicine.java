package com.dgut.medicalsystem.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 药物库存表
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 药品名
     */
    private String medicineName;

    /**
     * 药物库存数量
     */
    private Integer medicineStock;

    /**
     * 药物单价
     */
    private Double medicinePrice;

    /**
     * 药物是否可用  1---可用   0---不可用  
     */
    @TableLogic
    private Integer medicineStatus;

    /**
     * 版本锁
     */
    @Version
    private Integer version;

    /**
     * 药物类型 中药/西药/检查项目 
     */
    private String medicalType;

    /**
     * 规格
     */
    private String specifications;

    /**
     * 药物原价
     */
    private BigDecimal medicineOriginalPrice;


}
