package com.dgut.medicalsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 挂号申请
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppointmentApply implements Serializable {
    private static final long serialVersionUID = 1L;
    private String docId;
    private String symptom;
    private double appoPrice;
}
