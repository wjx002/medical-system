package com.dgut.medicalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 账单表
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 挂号费
     */
    private Double appoPrice;

    /**
     * 账单费用
     */
    private Double price;

    /**
     * 缴费日期
     */
    private LocalDateTime billDate;


}
