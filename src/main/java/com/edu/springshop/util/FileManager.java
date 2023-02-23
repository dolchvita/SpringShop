package com.edu.springshop.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springshop.domain.Pimg;
import com.edu.springshop.domain.Product;
import com.edu.springshop.exception.UploadException;

import oracle.net.aso.f;

@Component
public class FileManager {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	// 파일명 생성
	public String createFilename(String filename) {
		long time=System.currentTimeMillis();
		String ext=filename.substring(filename.lastIndexOf(".")+1, filename.length());
		return time+"."+ext;
	}
	
	
	// 지정된 디렉토리로 파일 저장
	public void save(Product product, String dir) throws UploadException{
		MultipartFile[] photoList=product.getPhoto();
		
		List<Pimg> pimgList=new ArrayList<Pimg>();	// 최종적으로 담을 리스트
		
		product.setPimgList(pimgList);		// 엮어 주기!
		
		
			try {
				for(int i=0; i<photoList.length; i++) {
					Thread.sleep(10);

					MultipartFile photo=photoList[i];
					
					Pimg pimg=new Pimg();
					pimg.setProduct(product);		// ** 이 시점 pk 존재
					pimg.setFilename(createFilename(photo.getOriginalFilename()));
					
					pimgList.add(pimg);	// 세팅된 pimg 추가
					
					photo.transferTo(new File(dir+pimg.getFilename()));
				}
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
				// 예외 전환
				throw new UploadException("파일 업로드 실패", e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new UploadException("파일 업로드 실패", e);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	
}
