package com.pj.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("home.homeController")
public class HomeController {
	@RequestMapping("/home")
	public String home() {	
		return "/home/home";	
	}
}
