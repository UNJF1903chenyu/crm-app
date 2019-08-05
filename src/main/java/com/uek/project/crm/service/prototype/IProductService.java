package com.uek.project.crm.service.prototype;

import java.util.List;

import com.github.pagehelper.Page;
import com.uek.project.crm.entity.Product;

public interface IProductService {
	public void createProduct(Product product);
	public List<Product> getAllProductService();
	Page<Product> getProducts(int pageNum,int pageSize);
}
