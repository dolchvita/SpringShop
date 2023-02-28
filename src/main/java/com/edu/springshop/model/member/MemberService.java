package com.edu.springshop.model.member;

import java.util.List;

import com.edu.springshop.domain.Member;

public interface MemberService {
	public List selectAll();
	public Member select(Member member);
	
	// 디비등록 + 암호화 + 이메일
	public void regist(Member member);
	public void update(Member member);
	
	// 디비삭제 + 이메일
	public void unregist(Member member);
	
}
