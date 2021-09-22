package com.dgut.medicalsystem.entity.dto;

import lombok.*;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MedicalStaffQuery {
    private static final long serialVersionUID = 1L;
    private String name;
    private String subject;
    private String name_zh;

}
