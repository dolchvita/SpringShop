package com.edu.springshop.model.member;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springshop.domain.Member;
import com.edu.springshop.exception.MemberException;

@Repository
public class MybatisMemberDAO implements MemberDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List selectAll() {
		return sqlSessionTemplate.selectList("Member.selectAll");
	}

	@Override
	public Member select(Member member) {
		return sqlSessionTemplate.selectOne("Member.select", member);
	}

	@Override
	public void insert(Member member) throws MemberException{
		
		int result=sqlSessionTemplate.insert("Member.insert", member);
		//result=0;
		if(result<1) {
			throw new MemberException("회원등록 실패");
		}
	}

	@Override
	public void update(Member member) throws MemberException{
		int result=sqlSessionTemplate.update("Member.update", member);
		if(result<1) {
			throw new MemberException("회원수정실패");
		}
	}

	@Override
	public void delete(Member member) throws MemberException{
		int result=sqlSessionTemplate.delete("Member.delete", member);
		if(result<1) {
			throw new MemberException("회원삭제실패");
		}
	}

	
}
