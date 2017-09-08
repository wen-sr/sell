package com.imooc.sell.service.impl;

import com.imooc.sell.dao.ProductInfoDao;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.enums.ProductStatus;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  12:48
 */
@Service
public class ProductInfoServiceImpl implements ProductService {

    @Autowired
    ProductInfoDao productInfoDao;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatus.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }


    @Override
    public void increaseStock(List<CartDTO> cartDTOList){
        for (CartDTO cartDto : cartDTOList){
            ProductInfo productInfo = productInfoDao.findOne(cartDto.getProductId());
            if(productInfo == null ) {
                throw new SellException(ProductStatus.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() + cartDto.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }
    }

    @Transactional
    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDto : cartDTOList){
            ProductInfo productInfo = productInfoDao.findOne(cartDto.getProductId());
            if(productInfo == null ) {
                throw new SellException(ProductStatus.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - cartDto.getProductQuantity();
            if(result < 0 ){
                throw new SellException(ProductStatus.PRODUCT_STOCK_NOT_ENOUGH);
            }
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }
    }


}
