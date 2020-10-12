package com.pj.home;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.cj.Session;
import com.pj.member.SessionInfo;

@Controller("home.homeController")
@RequestMapping("/home/*")
public class HomeController {
	@RequestMapping("home")
	public String home(
			HttpSession session
			) {	
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if(info!=null) {
				return "/bbs/list";
		}
		System.out.println("check");
		return "/home/home";	
	}
}
