package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ProductStatus;
import com.imooc.sell.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  13:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    ProductService productService;

    @Test
    public void findOne() throws Exception {
        System.out.println( productService.findOne("123456"));
    }

    @Test
    public void findUpAll() throws Exception {
        System.out.println(productService.findUpAll());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest request = new PageRequest(0, 2 );
        Page<ProductInfo> productInfo = productService.findAll(request);
        System.out.println(productInfo);
    }

}