package com.dgut.medicalsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@MapperScan("com.dgut.medicalsystem.mapper")
public class MedicalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalSystemApplication.class, args);
    }

}
