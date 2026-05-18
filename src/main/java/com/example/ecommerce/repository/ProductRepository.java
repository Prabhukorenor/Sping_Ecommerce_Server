package com.example.ecommerce.repository;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;

//import org.hibernate.query.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> 
{
//		Page findByCategoryIgnoreCase(String category,Pageable pageable);
//		
//		Page findByPriceBetween(BigDecimal min,BigDecimal max,Pageable pageable);
//		
	
	List<Product> findByCategory(String category);
	
	List<Product> findByPriceBetween(BigDecimal min,BigDecimal max);
	
    //List<Product> findAll(Pageable pageable);
	Page<Product> findAll(Pageable pageable);

	Page<Product> findByCategoryIgnoreCaseAndSubcategoryIgnoreCaseAndPriceBetween(String category, String subcategory,
			BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

	Page<Product> findByCategoryIgnoreCaseAndSubcategoryIgnoreCase(String category, String subcategory,
			Pageable pageable);

	Page<Product> findByCategoryIgnoreCaseAndPriceBetween(String category, BigDecimal minPrice, BigDecimal maxPrice,
			Pageable pageable);

	Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

	Page<Product> findByCategoryIgnoreCase(String category, Pageable pageable);

//	Product saveAll(Product existingProduct);
}
