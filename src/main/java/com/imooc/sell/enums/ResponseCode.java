package com.imooc.sell.enums;

import lombok.Getter;

/**
 * Created by geely
 */
@Getter
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),
    ORDER_STATUS_ERROR(11, "订单状态异常"),
    PRODUCT_NOT_EXIST(11, "商品不存在"),
    PRODUCT_STOCK_NOT_ENOUGH(12, "商品库存足"),
    ORDER_UPDATE_FAIL(13, "更新订单状态失败"),
    ORDER_DETAIL_EMPTY(14, "无订单详情"),
    ORDER_NOT_EXISTS(15, "订单不存在"),
    ORDER_PAY_STATUS_ERROR(16, "订单状态不正确"),
    PARAM_ERROR(17, "参数不正确"),
    ORDER_OWNER_ERROR(18, "该订单不属于你"),
    WECHAT_ERROR(19, "微信错误")
    ;

    private final int code;
    private final String desc;


    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }


}
