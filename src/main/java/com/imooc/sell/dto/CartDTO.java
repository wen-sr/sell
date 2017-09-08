package com.imooc.sell.dto;

import lombok.Data;

/**
 * Description: 购物车
 * User: wen-sr
 * Date: 2017-09-08  16:06
 */
@Data
public class CartDTO {

    //商品id
    private String productId;
    //数量
    private int productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, int productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
