package com.imooc.sell.exception;

import com.imooc.sell.enums.ProductStatus;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  15:38
 */
public class SellException extends RuntimeException {

    private int code;

    public SellException(ProductStatus productStatus) {
        super(productStatus.getMsg());
        this.code = productStatus.getCode();
    }
}
