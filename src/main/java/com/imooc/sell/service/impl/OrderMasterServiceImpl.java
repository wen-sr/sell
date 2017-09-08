package com.imooc.sell.service.impl;

import com.imooc.sell.dao.OrderDetailDao;
import com.imooc.sell.dao.OrderMasterDao;
import com.imooc.sell.dao.ProductInfoDao;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ProductStatus;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.KeyUtil;
import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  15:26
 */
@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    OrderMasterDao orderMasterDao;

    @Autowired
    ProductInfoDao productInfoDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    ProductService productService;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Transactional
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtil.getUniqueKey();

        List<CartDTO> cartDTOList = new ArrayList<>();

//        查询商品（数量，价格）
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoDao.findOne(orderDetail.getProductId());
            if(productInfo == null ){
                throw new SellException(ProductStatus.PRODUCT_NOT_EXIST);
            }
//        计算总价
            orderAmount = orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
//        写入订单到数据库（master，detail）
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailDao.save(orderDetail);

            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());

        }

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setBuyerAmount(orderAmount);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterDao.save(orderMaster);
//        扣库存
        productService.decreaseStock(cartDTOList);

        return null;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
