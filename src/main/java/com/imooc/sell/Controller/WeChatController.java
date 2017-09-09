package com.imooc.sell.Controller;

import com.imooc.sell.enums.ResponseCode;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-09  17:43
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Autowired
    WxMpService wxMpService;

    @GetMapping("/auth")
    public String auth(@RequestParam("returnUrl") String returnUrl){
//        配置
//        调用方法
        String url = "http://wensr.mynatapp.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url , WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        System.out.println(redirectUrl);

        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】 失败", e);
            throw new SellException(ResponseCode.WECHAT_ERROR);
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        System.out.println(openId);
        return "redirect:" + returnUrl + "?openid=" + openId;
    }
}
