package com.wisedu.wechat.mp.util;

import com.wisedu.wechat.config.WiseduConfig;
import com.wisedu.wechat.util.SpringUtil;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;

/**
 * @author 刘金泳
 * @Date 2019/7/22
 */
public class MpUtil {

    public static WxMpConfigStorage getWxMpConfigStorage(String corpSecret){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(SpringUtil.getBean(WiseduConfig.class).getAppId());
        wxMpInMemoryConfigStorage.setSecret(corpSecret);
        return wxMpInMemoryConfigStorage;
    }

}
