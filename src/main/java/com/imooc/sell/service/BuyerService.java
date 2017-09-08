package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-09  0:18
 */
public interface BuyerService {

//    查询一个订单
    OrderDTO findOrderOne(String openid, String orderid);
//    取消订单
    OrderDTO cancelOrder(String openid, String orderid);
}
