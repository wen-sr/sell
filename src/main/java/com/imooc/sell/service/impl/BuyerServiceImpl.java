package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResponseCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-09  0:20
 */
@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    OrderMasterService orderMasterService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderid) {
        OrderDTO orderDTO = orderMasterService.findOne(orderid);
        if(orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ResponseCode.ORDER_STATUS_ERROR);
        }
        return orderDTO;



    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderid) {
        OrderDTO orderDTO = findOrderOne(openid, orderid);
        if(orderDTO == null) {
            throw new SellException(ResponseCode.ORDER_NOT_EXISTS);
        }

        return orderMasterService.cancel(orderDTO);


    }
}
