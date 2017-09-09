package com.imooc.sell.Controller;

import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ProductVo;
import com.imooc.sell.common.ServerResponse;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  13:19
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductInfoController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;


    /**
     * 查询所有上架商品
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<List<ProductVo>> list(){
        //在架商品列表信息
        List<ProductInfo> productInfos = productService.findUpAll();

        List<Integer> categoryIds = new ArrayList<Integer>();
        //把在架商品所在的类目查询出来
        for(ProductInfo p : productInfos ) {
            categoryIds.add(p.getCategoryType());
        }
        //查询所有有商品信息的类目信息
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryIds);

        List<ProductVo> productVos = new ArrayList<>();
        for(ProductCategory pc : productCategories){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(pc.getCategoryName());
            productVo.setCategoryType(pc.getCategoryType());

            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            for(ProductInfo p : productInfos){
               if(p.getCategoryType() == pc.getCategoryType()){
                   ProductInfoVO productInfoVO = new ProductInfoVO();
                   productInfoVO.setProductDescription(p.getProductDescription());
                   productInfoVO.setProductIcon(p.getProductIcon());
                   productInfoVO.setProductId(p.getProductId());
                   productInfoVO.setProductName(p.getProductName());
                   productInfoVO.setProductPrice(p.getProductPrice());
                   productInfoVOS.add(productInfoVO);
               }
            }
            productVo.setProductVos(productInfoVOS);
            productVos.add(productVo);
        }

        return ServerResponse.createBySuccess("成功",productVos);
    }

}
