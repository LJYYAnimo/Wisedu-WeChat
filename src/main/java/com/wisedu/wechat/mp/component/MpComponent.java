package com.wisedu.wechat.mp.component;

import com.wisedu.wechat.mp.util.MpUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author 刘金泳
 * @Date 2019/7/22
 */
@Component
public class MpComponent {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 获取AccessToken 缓存
     * @param corpSecret
     * @return
     */
    @Cacheable(value = "wechat",key = "#corpSecret")
    public String getAccessToken(String corpSecret){
        wxMpService.setWxMpConfigStorage(MpUtil.getWxMpConfigStorage(corpSecret));
        try {
            return wxMpService.getAccessToken();
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
            WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = (WxMpInMemoryConfigStorage)wxMpService.getWxMpConfigStorage();
            wxMpInMemoryConfigStorage.setAccessToken(getAccessToken(corpSecret));
            wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
            return wxMpService.getJsapiTicket();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }



}
