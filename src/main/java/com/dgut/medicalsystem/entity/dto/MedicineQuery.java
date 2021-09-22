package com.dgut.medicalsystem.entity.dto;

import lombok.*;

/**
 * 放置药物库存的查询条件
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MedicineQuery extends Pagination {
    private static final long serialVersionUID = 1L;
    private String medicineName;
    private String medicalType;
    private Integer medicineStatus;
}
