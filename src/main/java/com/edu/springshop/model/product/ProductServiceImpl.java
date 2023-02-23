package com.edu.springshop.model.product;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springshop.domain.Pimg;
import com.edu.springshop.domain.Product;
import com.edu.springshop.exception.ProductException;
import com.edu.springshop.util.FileManager;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private PimgDAO pimgDAO;
	@Autowired
	private FileManager fileManager;
	
	
	@Override
	public List selectAll() {
		return productDAO.selectAll();
	}
	
	@Override
	public Product select(int product_idx) {
		return productDAO.select(product_idx);
	}
	
	@Override
	public void regist(Product product, String dir) {
		productDAO.insert(product);		// pk 존재

		fileManager.save(product, dir);
		
		List<Pimg> pimgList=product.getPimgList();
		for(Pimg pimg: pimgList) {
			pimgDAO.insert(pimg);
		}
		
		
	}
	
	@Override
	public void update(Product product) {
		
	}
	
	@Override
	public void delete(int product_idx) {
		
	}
	

}
