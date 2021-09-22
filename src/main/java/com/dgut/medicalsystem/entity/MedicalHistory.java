package com.dgut.medicalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
@ToString
public class MedicalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 病历表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 诊断记录id
     */
    private Long recordId;

    /**
     * 病人id
     */
    private Integer patientId;

    /**
     * 账单id
     */
    private Long billId;

    /**
     * 挂号ID
     */
    private Long appointmentId;

    /**
     * 医生ID
     */
    private Integer doctorId;

    /**
     * 治疗完成日期
     */
    private LocalDateTime recordDate;


}
