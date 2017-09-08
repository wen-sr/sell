package com.imooc.sell.enums;

import lombok.Getter;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  12:49
 */
@Getter
public enum ProductStatus {

    UP(0, "在架"),
    DOWN(1, "下架"),
    ;


    private int code;
    private String msg;

    ProductStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
