package com.pj.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("member.memberController")
public class MemberController { 
	@Autowired
	private MemberService service;
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	private String login(
			HttpSession session,
			@RequestParam String userId,
			@RequestParam String userPwd,
			Model model
			) {
		
		Member dto = service.login(userId);
		
		return "redirect:/bbs/list";
	}
	

}
