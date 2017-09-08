package com.imooc.sell.enums;

import lombok.Getter;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  14:48
 */
@Getter
public enum PayStatus {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功")
    ;

    private int code;
    private String msg;

    PayStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
