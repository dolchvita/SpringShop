package com.edu.springshop.model.product;

import java.util.List;

import com.edu.springshop.domain.Cart;

public interface CartDAO {

	public List selectAll(int member_idx);
	public int select(Cart cart);
	public void insert(Cart cart);
	public void updateEa(Cart cart);
	
}
