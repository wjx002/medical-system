package com.dgut.medicalsystem.entity.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AppointmentInfo extends Pagination {
    private static final long serialVersionUID = 1L;
    private String docId;
    private String name;
    private String subject;
    private String proTitle;
    private String roomNo;
    private int amount;
}
