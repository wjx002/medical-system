package com.dgut.medicalsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 进度查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Progress implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appointStatus;
    private String appointTime;
    private String CureStatus;
    private String CureTime;
    private String QueueNumber;
    private String BillStatus;
    private String BillTime;
    private String FinishStatus;
    private String FinishTime;
}
