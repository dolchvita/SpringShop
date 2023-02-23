package com.edu.springshop.model.product;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springshop.domain.Pimg;
import com.edu.springshop.exception.PimgException;

@Repository
public class MtbatisPimgDAO implements PimgDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTeamlpate;
	
	
	@Override
	public List selectByProduct(int product_idx) {
		return sqlSessionTeamlpate.selectList("Pimg.selectByProduct", product_idx);
	}

	
	@Override
	public void insert(Pimg pimg) throws PimgException{
		int result=sqlSessionTeamlpate.insert("Pimg.insert", pimg);
		if(result<1) {
			throw new PimgException("사진 저장 실패"); 
		}
		
	}
	

	@Override
	public void delete(int product_idx) throws PimgException{
		int result=sqlSessionTeamlpate.delete("Pimg.delete", product_idx);
		if(result<1) {
			throw new PimgException("사진 삭제 실패"); 
		}
	}

}
