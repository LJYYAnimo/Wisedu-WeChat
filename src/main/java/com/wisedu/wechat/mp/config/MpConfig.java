package com.wisedu.wechat.mp.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 刘金泳
 * @Date 2019/7/23
 */
@Component
public class MpConfig {

    @Bean
    public WxMpService wxMpService (){
        WxMpService wxMpService = new WxMpServiceImpl();
        return wxMpService;
    }

}
