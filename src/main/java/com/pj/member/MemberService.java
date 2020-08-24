package com.pj.member;

public interface MemberService {
	public void insertMember() throws Exception;
	public Member login(String userId);
}
