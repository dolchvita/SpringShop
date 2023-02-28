package com.edu.springshop.model.product;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springshop.domain.Pimg;
import com.edu.springshop.domain.Product;
import com.edu.springshop.exception.PimgException;
import com.edu.springshop.exception.ProductException;
import com.edu.springshop.exception.UploadException;
import com.edu.springshop.util.FileManager;

@Service
public class ProductServiceImpl implements ProductService{
	private Logger logger=LoggerFactory.getLogger(this.getClass());
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
	@Transactional(propagation = Propagation.REQUIRED)
	public void regist(Product product, String dir) throws ProductException, UploadException, PimgException{
		productDAO.insert(product);		// pk 존재

		fileManager.save(product, dir);
		
		List<Pimg> pimgList=product.getPimgList();
		
		//logger.info("사진 리스트가 담기는 모습~~"+pimgList);

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
