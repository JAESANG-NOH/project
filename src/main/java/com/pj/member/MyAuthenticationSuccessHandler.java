package com.pj.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	@Autowired
	private MemberService service;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		String userId = authentication.getName();
		Member dto = service.readMember(userId);
		SessionInfo info = new SessionInfo();
		info.setUserId(dto.getUserId());
		
		HttpSession session = request.getSession();
		session.setAttribute("member", info);
		
		super.onAuthenticationSuccess(request, response, authentication);
		System.out.println("check2");
	}
	
	
}
