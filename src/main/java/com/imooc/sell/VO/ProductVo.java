package com.imooc.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Description: 商品（包含类目）
 * User: wen-sr
 * Date: 2017-09-08  13:25
 */
@Data
public class ProductVo {

    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("food")
    private List<ProductInfoVO> productVos;
}
