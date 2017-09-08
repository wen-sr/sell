package com.imooc.sell.exception;

import com.imooc.sell.enums.ProductStatus;
import com.imooc.sell.enums.ResponseCode;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  15:38
 */
public class SellException extends RuntimeException {

    private int code;

    public SellException(ResponseCode responseCode) {
        super(responseCode.getDesc());
        this.code = responseCode.getCode();
    }
}
