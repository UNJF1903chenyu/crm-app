package com.uek.project.crm;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.uek.project.crm.dao.IProductDao;
import com.uek.project.crm.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrmApplicationTests {
	@Autowired
	private IProductDao productDao;
	@Test
	public void insertProduct(){
		for (int i = 0; i <10; i++) {
			productDao.save(new Product("IPad"+i,i*100));
		}
	}
	@Test
	public void findProducts() {
		List<Product> products = productDao.findAll();
		for (Product product : products) {
			System.out.println(product);
		}
	}

}
