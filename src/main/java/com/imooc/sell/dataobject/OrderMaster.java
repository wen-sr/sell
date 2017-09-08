package com.imooc.sell.dataobject;

import com.imooc.sell.enums.OrderStatus;
import com.imooc.sell.enums.PayStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  14:42
 */
@Entity
@Data
public class OrderMaster {

    @Id
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

//    @Transient//忽略数据库是否有外键关系
//    private List<OrderDetail> orderDetailList;
}
