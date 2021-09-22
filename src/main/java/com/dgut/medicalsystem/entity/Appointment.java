package com.dgut.medicalsystem.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 挂号表
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 挂号日期
     */
    private LocalDateTime appointmentDate;

    /**
     * 症状
     */
    private String symptom;



}
