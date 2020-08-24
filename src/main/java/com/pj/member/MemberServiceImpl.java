package com.pj.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.common.dao.CommonDAO;

@Service("Member.memberServiece")
public class MemberServiceImpl implements MemberService{
@Autowired
private CommonDAO dao;
	
	@Override
	public void insertMember() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Member login(String userId) {
		Member dto = null;
		try {
			dto = dao.selectOne("",userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

}
