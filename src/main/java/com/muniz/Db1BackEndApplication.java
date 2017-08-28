package com.muniz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Db1BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(Db1BackEndApplication.class, args);
	}
	
	/*
	@CrossOrigin
	@RequestMapping(value="/checkPassword/{password}",method=RequestMethod.GET)
	public Integer passwordMeter(@PathVariable String password){
		PasswordMeter passwordMeter = new PasswordMeter();
		//return passwordMeter.scorePasswordDummy(password);
		return passwordMeter.scorePassword(password);
	}
	*/
	
	@CrossOrigin
	@RequestMapping(value="/checkPassword",method=RequestMethod.POST)
	public Integer passwordMeter(@RequestParam("password") String password){
		PasswordMeter passwordMeter = new PasswordMeter();
		//return passwordMeter.scorePasswordDummy(password);
		return passwordMeter.scorePassword(password);
	}
}
