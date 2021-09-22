package com.dgut.medicalsystem.entity.dto;

import lombok.Data;

@Data
public class RegistUser {
    private String password;
    private String email;
    private String confirm;
    private String tel;

}
