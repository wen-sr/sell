package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  11:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @Test
    public void findOne() throws Exception {
        Assert.assertEquals(1, categoryService.findOne(1).getCategoryType());
    }

    @Test
    public void findAll() throws Exception {
        Assert.assertEquals(2, categoryService.findAll().size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        Assert.assertEquals(2, categoryService.findByCategoryTypeIn(Arrays.asList(1,2)).size());
    }

    @Test
    public void save() throws Exception {
        Assert.assertNotNull(categoryService.save(new ProductCategory("免费", 3)));
    }

}