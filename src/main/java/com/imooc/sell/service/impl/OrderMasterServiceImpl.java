package com.imooc.sell.service.impl;

import com.imooc.sell.convert.PageOrderMastr2PageOrderDTO;
import com.imooc.sell.dao.OrderDetailDao;
import com.imooc.sell.dao.OrderMasterDao;
import com.imooc.sell.dao.ProductInfoDao;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatus;
import com.imooc.sell.enums.PayStatus;
import com.imooc.sell.enums.ProductStatus;
import com.imooc.sell.enums.ResponseCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.KeyUtil;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
@Slf4j
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
                throw new SellException(ResponseCode.PRODUCT_NOT_EXIST);
            }
//        计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
//        写入订单到数据库（master，detail）
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailDao.save(orderDetail);

            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setBuyerAmount(orderAmount);
        orderMasterDao.save(orderMaster);
//        扣库存
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if(orderMaster == null ){
            log.error("[查询订单] 查询订单失败，订单不存在，orderid={}", orderId);
            throw new SellException(ResponseCode.ORDER_NOT_EXISTS);
        }
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetails)){
            log.error("[查询订单详情] 查询订单详情失败， orderId={}", orderId );
            throw new SellException(ResponseCode.ORDER_DETAIL_EMPTY);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        return PageOrderMastr2PageOrderDTO.getPageOrderDTO(orderMasterPage, pageable);
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
       Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        return PageOrderMastr2PageOrderDTO.getPageOrderDTO(orderMasterPage, pageable);
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

//        判断订单状态
        if(orderDTO.getOrderStatus() != OrderStatus.NEW.getCode()){
            log.error("[取消订单] 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResponseCode.ORDER_STATUS_ERROR);
        }
//        修改订单状态
        orderMaster.setOrderStatus(OrderStatus.CANCEL.getCode());
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult == null) {
            log.error("[取消订单] 更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResponseCode.ORDER_UPDATE_FAIL);
        }
//        返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 订单中无商品详情， orderId=", orderDTO.getOrderId());
            throw new SellException(ResponseCode.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(OrderDetail od : orderDTO.getOrderDetailList()){
            CartDTO cartDTO = new CartDTO();
            BeanUtils.copyProperties(od, cartDTO);
            cartDTOList.add(cartDTO);
        }
        productService.increaseStock(cartDTOList);
//        如果已支付的话,需要退款
        if(orderDTO.getPayStatus() == PayStatus.SUCCESS.getCode()){
            //todo
        }

        orderDTO.setOrderStatus(OrderStatus.CANCEL.getCode());
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
//        判断订单状态
        if(orderDTO.getOrderStatus() != OrderStatus.NEW.getCode()){
            log.error("[完结订单] 订单状态不正确，orderId={}, orderStatus=", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResponseCode.ORDER_STATUS_ERROR);
        }
//        修改订单状态
        orderDTO.setOrderStatus(OrderStatus.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult == null ){
            log.error("【完结订单】 更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResponseCode.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
//        判断订单状态
        if(orderDTO.getOrderStatus() != OrderStatus.NEW.getCode()){
            log.error("[订单支付] 订单状态不正确，orderId={}, orderStatus=", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResponseCode.ORDER_STATUS_ERROR);
        }
//        判断支付状态
        if(orderDTO.getPayStatus() != PayStatus.WAIT.getCode()){
            log.error("[订单支付] 订单支付状态不正确，orderId={}, orderStatus=", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResponseCode.ORDER_PAY_STATUS_ERROR);
        }
//        修改支付状态
        orderDTO.setPayStatus(PayStatus.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if(updateResult == null ){
            log.error("【订单支付】 更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResponseCode.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
