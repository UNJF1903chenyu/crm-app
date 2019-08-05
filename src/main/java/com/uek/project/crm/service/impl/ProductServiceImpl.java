package com.uek.project.crm.service.impl;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uek.project.crm.dao.IProductDao;
import com.uek.project.crm.entity.Product;
import com.uek.project.crm.service.prototype.IProductService;
@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductDao iProductDao;
	@Autowired 
	private RedisTemplate<Object, Object> rt;
	
	@Override
	public void createProduct(Product product) {
		iProductDao.save(product);
	}

	@Override
	@Cacheable(value="chenyu")
	public List<Product> getAllProductService() {
	/*	List<Product> ps = null;
		ps = (List<Product>)rt.opsForValue().get("estore-products");
		if(ps == null){
			System.out.println("cache data first.");
			ps = iProductDao.findAll();
			rt.opsForValue().set("estore-products", ps, Duration.ofSeconds(3600));
		}
		return ps;*/
		return iProductDao.findAll();
	}

	@Override
	@Cacheable(value="products",key="#pageNum+'_'+#pageSize")
	public Page<Product> getProducts(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Product> data = (Page<Product>)iProductDao.findAll();
		return data;
		
	}

}
