package com.uek.project.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.Alias;

import com.uek.project.crm.entity.Product;
/**
 * 产品Dao
 * @author l
 *
 */

public interface IProductDao {
	@Select("select * from t_product")
	List<Product>findAll();
	//@Insert("insert into t_product (name,price) values(#{name},#{price})")
	 void save(Product product);
}
