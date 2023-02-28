package com.edu.springshop.model.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.springshop.domain.Member;
import com.edu.springshop.exception.EmailException;
import com.edu.springshop.exception.HashException;
import com.edu.springshop.exception.MemberException;
import com.edu.springshop.util.EmailManager;
import com.edu.springshop.util.PassConverter;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private PassConverter passConverter;
	@Autowired
	private EmailManager emailManager;
	Logger logger=LoggerFactory.getLogger(this.getClass());

	@Override
	public List selectAll() {
		return memberDAO.selectAll();
	}

	@Override
	public Member select(Member member) {
		return memberDAO.select(member);
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public void regist(Member member) throws HashException, EmailException, MemberException{
		String hash=passConverter.convertHash(member.getPass());
		
		member.setPass(hash);		// 패스워드를 해쉬값으로 대체
		
		//emailManager.send(member);
		
		memberDAO.insert(member);
	}

	@Override
	public void update(Member member) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregist(Member member) {
		// TODO Auto-generated method stub
		
	}

}
