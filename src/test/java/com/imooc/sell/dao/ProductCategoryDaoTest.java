package com.imooc.sell.dao;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-07  22:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Test
    public void findOneTest(){
        ProductCategory p = productCategoryDao.findOne(1);
        System.out.println(p.toString());
    }

    @Test
    @Transactional
    public void save(){
        ProductCategory p = new ProductCategory();
        p.setCategoryName("解决");
        p.setCategoryType(3);
        p = productCategoryDao.save(p);
        System.out.println(p.toString());
    }

    @Test
    public void update(){
        ProductCategory p = productCategoryDao.findOne(1);
        p.setCategoryName("好东西");
        productCategoryDao.save(p);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(1,2);

        productCategoryDao.findByCategoryTypeIn(list);
    }

}