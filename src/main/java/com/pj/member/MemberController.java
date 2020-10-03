package com.pj.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("member.memberController")
@RequestMapping("/member/*")
public class MemberController { 
	@Autowired
	private MemberService service;
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	private String login(
			HttpSession session,
			@RequestParam String userId,
			@RequestParam String userPwd,
			Model model
			) {
			Member dto = null;
		try {
			dto = service.login(userId);
			if(dto==null||!userPwd.equals(dto.getUserPwd())) {
				model.addAttribute("message","아이디 또는 패스워드가 일치하지 않습니다.");
				return "/home/home";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SessionInfo info = new SessionInfo();
		info.setUserId(dto.getUserId());
		info.setUserName(dto.getUserName());
		
		session.setAttribute("member", info);
		
		model.addAttribute("dto", dto);
		
		return "redirect:/bbs/list";
	}
	

}
