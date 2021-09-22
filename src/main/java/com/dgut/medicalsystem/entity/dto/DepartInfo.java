package com.dgut.medicalsystem.entity.dto;

import com.dgut.medicalsystem.entity.Department;
import com.dgut.medicalsystem.entity.MedicalStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class DepartInfo {
    Department department;
    List<MedicalStaff> staffs;
}
