package com.uek.project.crm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	@Delete("delete from t_product where id=#{id}")
	void delete(int id);
	@Update("update t_product set name=#{name},price=#{price} where id=#{id}")
	void update(Product product);
}
