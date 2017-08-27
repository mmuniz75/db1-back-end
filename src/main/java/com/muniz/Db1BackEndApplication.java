package com.muniz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Db1BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(Db1BackEndApplication.class, args);
	}
	
	@CrossOrigin
	@RequestMapping(value="/checkPassword/{password}",method=RequestMethod.GET)
	public Integer passwordMeter(@PathVariable String password){
		if(password==null)
			return 0;
		
		 switch(password.length()) {
         case 1 :
             return 19;
         case 2:
             return 39;
         case 3:
             return 61;       
         case 4:
             return 81;    
         case 5:
             return 100;
         default:
             return 0;
     }   
		
	}
}
