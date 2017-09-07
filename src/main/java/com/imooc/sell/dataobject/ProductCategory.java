package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Description: 商品类目
 * User: wen-sr
 * Date: 2017-09-07  22:05
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    @Id
    @GeneratedValue
    private int categoryId;
    private String categoryName;
    private int categoryType;
    private Date createTime;
    private Date updateTime;

}
