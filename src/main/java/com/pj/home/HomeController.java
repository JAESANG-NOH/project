package com.pj.home;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pj.member.SessionInfo;

@Controller("home.homeController")
@RequestMapping("/home/*")
public class HomeController {
	
	@RequestMapping("home")
	public String home(
			HttpSession session,
			Model model
			) {
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if(info!=null) {
				return "redirect:/bbs/list";
		}
		return "/home/home";	
	}
}
