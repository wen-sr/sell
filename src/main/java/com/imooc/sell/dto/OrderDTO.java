package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.enums.OrderStatus;
import com.imooc.sell.enums.PayStatus;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  15:23
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private List<OrderDetail> orderDetailList = new ArrayList<>();
}
