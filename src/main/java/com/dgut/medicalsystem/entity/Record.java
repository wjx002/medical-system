package com.dgut.medicalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 诊断记录表
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 病情描述
     */
    private String cureDescription;

    /**
     * 诊断记录状态 2---已就诊  1---就诊中 0---待就诊 
     */
    private Integer cureStatus;

    /**
     * 诊断日期
     */
    private LocalDateTime recordDate;


}
