package com.imooc.sell.convert;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-09  22:58
 */
public class PageOrderMastr2PageOrderDTO {

    public static Page<OrderDTO> getPageOrderDTO(Page<OrderMaster> orderMasterPage, Pageable pageable) {
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderMaster om : orderMasterList) {
            OrderDTO o = new OrderDTO();
            BeanUtils.copyProperties(om, o);
            orderDTOList.add(o);
        }

        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
