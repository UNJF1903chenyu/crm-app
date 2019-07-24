package com.uek.project.crm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello Spring Boot
 * @author l
 *
 */
@Controller
public class HelloController {
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		return "hello spring boot";
	}
}