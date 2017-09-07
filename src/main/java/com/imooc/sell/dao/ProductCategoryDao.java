package com.imooc.sell.dao;

import com.imooc.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-07  22:18
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {
}
