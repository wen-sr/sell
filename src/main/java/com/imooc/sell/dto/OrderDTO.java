package com.imooc.sell.dto;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.enums.OrderStatus;
import com.imooc.sell.enums.PayStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  15:23
 */
@Data
public class OrderDTO {

    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal buyerAmount;
    private int orderStatus = OrderStatus.NEW.getCode();
    private int payStatus = PayStatus.WAIT.getCode();
    private Date createTime;
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
