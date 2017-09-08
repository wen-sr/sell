package com.imooc.sell.dao;

import com.imooc.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  12:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {

    @Autowired
    ProductInfoDao productInfoDao;

    @Test
    public void findByProductStatus() throws Exception {
        ProductInfo p = new ProductInfo();
        p.setProductId("123456");
        p.setProductName("皮蛋粥");
        p.setProductPrice(new BigDecimal(3.2));
        p.setProductDescription("好吃");
        p.setProductIcon("http://DDD.jpg");
        p.setProductStatus(0);
        p.setProductStock(100);
        p.setCategoryType(1);
        productInfoDao.save(p);
    }

    @Test
    public void findByProductStatusTest(){
        Assert.assertEquals(1, productInfoDao.findByProductStatus(0).size());
    }
}