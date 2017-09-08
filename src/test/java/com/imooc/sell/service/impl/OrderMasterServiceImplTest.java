package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatus;
import com.imooc.sell.enums.PayStatus;
import com.imooc.sell.service.OrderMasterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  18:09
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterServiceImplTest {

    @Autowired
    OrderMasterService orderMasterService;

    String buyerOpenid = "999999";
    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid(buyerOpenid);
        orderDTO.setBuyerAddress("高老庄");
        orderDTO.setBuyerName("bajie");
        orderDTO.setBuyerPhone("02313431313");
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("584665");
        o1.setProductQuantity(100);
        orderDetailList.add(o1);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123456");
        o2.setProductQuantity(2);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);
        orderMasterService.create(orderDTO);
    }

    @Test
    public void findOne() throws Exception {
        OrderDTO orderDTO = orderMasterService.findOne("1504866680538650171");
        System.out.println(orderDTO);
    }

    @Test
    public void findList() throws Exception {
        PageRequest request = new PageRequest(0, 2);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(buyerOpenid, request);
        System.out.println(orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderMasterService.findOne("1504866933770458153");
        OrderDTO result = orderMasterService.cancel(orderDTO);
        System.out.println(result);

    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderMasterService.findOne("1504867188050684173");
        OrderDTO result = orderMasterService.finish(orderDTO);
        Assert.assertEquals(OrderStatus.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderMasterService.findOne("1504867188050684173");
        OrderDTO result = orderMasterService.paid(orderDTO);
        Assert.assertEquals(PayStatus.SUCCESS.getCode(), result.getPayStatus());
    }

}