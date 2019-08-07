package com.wisedu.wechat.cp.config;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 刘金泳
 * @Date 2019/7/22
 */
@Component
public class CpConfig {

    @Bean
    public WxCpService wxCpService (){
        WxCpService wxCpService = new WxCpServiceImpl();
        return wxCpService;
    }

}
