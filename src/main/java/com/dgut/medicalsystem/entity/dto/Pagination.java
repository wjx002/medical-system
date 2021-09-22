package com.dgut.medicalsystem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页的页码和大小
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Pagination implements Serializable {
    private static final long serialVersionUID = 1L;
    private int page;
    private int size;
}
