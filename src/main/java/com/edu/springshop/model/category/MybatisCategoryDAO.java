package com.edu.springshop.model.category;

import java.util.List;

import org.apache.ibatis.cache.CacheException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springshop.domain.Category;
import com.edu.springshop.exception.CategoryException;

@Repository
public class MybatisCategoryDAO implements CategoryDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List selectAll() {
		return sqlSessionTemplate.selectList("Category.selectAll");
	}

	@Override
	public Category select(int category_idx) {
		return sqlSessionTemplate.selectOne("Category.select", category_idx);
	}

	@Override
	public void insert(Category category) throws CategoryException{
		int result=sqlSessionTemplate.insert("Category.insert", category);
		if(result<1) {
			throw new CategoryException("등록 실패");
		}
	}

	@Override
	public void update(Category category) throws CategoryException{
		int result=sqlSessionTemplate.update("Category.update", category);
		if(result<1) {
			throw new CategoryException("수정 실패");
		}
	}

	@Override
	public void delete(int category_idx) throws CategoryException{
		int result=sqlSessionTemplate.delete("Category.delete", category_idx);
		if(result<1) {
			throw new CategoryException("삭제 실패");
		}
	}

}
