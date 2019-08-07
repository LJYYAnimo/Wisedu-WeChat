package com.wisedu.wechat.cp.component;

import com.wisedu.wechat.cp.util.CpUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author 刘金泳
 * @Date 2019/7/22
 */
@Component
public class CpComponent {

    @Autowired
    private WxCpService wxCpService;

    /**
     * 获取AccessToken 缓存
     * @param corpSecret
     * @return
     */
    @Cacheable(value = "wechat",key = "#corpSecret")
    public String getAccessToken(String corpSecret){
        System.out.println("getAccessToken没输出就是缓存了");
        wxCpService.setWxCpConfigStorage(CpUtil.getWxCpConfigStorage(corpSecret));
        try {
            return wxCpService.getAccessToken();
        } catch (WxErrorException e) {
            e.printStackTrace();
            System.out.println("微信获取出错，corpSecret="+corpSecret);
        }
        return null;
    }

    /**
     * 获取签名的ticket
     * @param corpSecret
     * @return
     */
    @Cacheable(value = "wechat" , key = "#corpSecret.concat('-').concat('ticket')")
    public String getTicket(String corpSecret){
        try {
            getAccessToken(corpSecret);
            System.out.println("getTicket没输出就是缓存了");
            WxCpInMemoryConfigStorage wxCpInMemoryConfigStorage = (WxCpInMemoryConfigStorage)wxCpService.getWxCpConfigStorage();
            wxCpInMemoryConfigStorage.setAccessToken(getAccessToken(corpSecret));
            wxCpService.setWxCpConfigStorage(wxCpInMemoryConfigStorage);
            return wxCpService.getJsapiTicket();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }



}
