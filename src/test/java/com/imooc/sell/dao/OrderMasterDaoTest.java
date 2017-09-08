package com.imooc.sell.dao;

import com.imooc.sell.dataobject.OrderMaster;
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
 * Date: 2017-09-08  15:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    OrderMasterDao orderMasterDao;

    @Test
    public void findByOpenid() throws Exception {
        PageRequest request = new PageRequest(0, 2);

        Page<OrderMaster> result = orderMasterDao.findByBuyerOpenid("999999", request);


    }

}