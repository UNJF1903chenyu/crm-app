package com.uek.project.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan({"com.uek.project.crm.dao"})
@EnableCaching
public class CrmAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmAppApplication.class, args);
	}

}
