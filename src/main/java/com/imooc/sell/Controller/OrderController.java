package com.imooc.sell.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.common.ServerResponse;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResponseCode;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  22:48
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class OrderController {

    @Autowired
    OrderMasterService orderMasterService;

    @Autowired
    BuyerService buyerService;

//    创建订单
    @PostMapping("/create")
    public ServerResponse<Map<String, Object>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确，错误信息为：", bindingResult.getFieldError().getDefaultMessage());
            throw new SellException(ResponseCode.PARAM_ERROR);
        }
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO.setBuyerName(orderForm.getName());
            orderDTO.setBuyerOpenid(orderForm.getOpenid());
            orderDTO.setBuyerPhone(orderForm.getPhone());
            orderDTO.setBuyerAddress(orderForm.getAddress());
            Gson gson = new Gson();
            List<OrderDetail> orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
            orderDTO.setOrderDetailList(orderDetailList);
        }catch (Exception e){
            throw new SellException(ResponseCode.PARAM_ERROR);
        }

        OrderDTO createResult = orderMasterService.create(orderDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ServerResponse.createBySuccess("成功", map);
    }
//    订单列表
    @RequestMapping("/list")
    public ServerResponse<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "3") int size){
        if(StringUtils.isEmpty(openid)){
            throw new SellException(ResponseCode.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(openid, request);
        return ServerResponse.createBySuccess("成功",orderDTOPage.getContent());

    }

//    订单详情
    @PostMapping("/detail")
    public ServerResponse<OrderDTO> detail(@RequestParam("openid") String openid,
                                           @RequestParam("orderid") String orderId){

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);

        return ServerResponse.createBySuccess("成功", orderDTO);

    }
//    取消订单
    @PostMapping("/cancel")
    public ServerResponse cancel(@RequestParam("openid") String openid,
                                 @RequestParam("orderid") String orderId){
        buyerService.cancelOrder(openid, orderId);
        return ServerResponse.createBySuccess("成功");
    }
}
