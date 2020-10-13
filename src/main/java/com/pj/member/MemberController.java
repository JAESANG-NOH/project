package com.pj.member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("member.memberController")
@RequestMapping("/member/*")
public class MemberController { 
	@RequestMapping(value="login", method=RequestMethod.POST)
	private String loginForm(
			String login_error,
			Model model
			) {
		System.out.println(login_error+":"+model);
		boolean bLoginError = login_error != null;
		if(bLoginError) {
			String msg = "아이디 혹은 비밀번호 오류";
			model.addAttribute("message",msg);
		}
		System.out.println("check_memcon");
		return "/home/home";
	}
	
	@RequestMapping(value="noAuthorized")
	public String noAuthorized() throws Exception{
		return "/error/noAuthorized";
	}
	
	@RequestMapping(value="expired")
	public String expired() throws Exception{
		return "/error/expired";
	}
}
