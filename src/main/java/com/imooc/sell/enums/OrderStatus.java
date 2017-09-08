package com.imooc.sell.enums;

import lombok.Getter;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  14:45
 */
@Getter
public enum OrderStatus {

    NEW(0, "新订单"),
    FINISHED(10, "订单结束"),
    CANCEL(5, "订单取消")
    ;

    private int code;
    private String msg;

    OrderStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
