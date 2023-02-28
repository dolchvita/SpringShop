package com.edu.springshop.model.admin;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springshop.domain.Admin;
import com.edu.springshop.exception.AdminException;
@Repository
public class MybatisAdminMapper implements AdminDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public Admin select(Admin admin) throws AdminException{
		Admin obj=sqlSessionTemplate.selectOne("Admin.select", admin);
		
		// 아무것도 입력하지 않을 경우 예외 일으키기
		if(obj==null) {
			throw new AdminException("로그인 정보가 올바르지 않습니다");
		}
		return obj;
	}

	@Override
	public void insert(Admin admin) throws AdminException{
		int result=sqlSessionTemplate.insert("Admin.insert", admin);
		if(result<1) {
			throw new AdminException("등록 실패");
		}
	}
	

}
