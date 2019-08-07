package com.wisedu.wechat.cp.util;

import com.wisedu.wechat.config.WiseduConfig;
import com.wisedu.wechat.util.SpringUtil;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;

/**
 * @author 刘金泳
 * @Date 2019/7/22
 */
public class CpUtil {

    public static WxCpConfigStorage getWxCpConfigStorage(String corpSecret){
        WxCpInMemoryConfigStorage wxCpInMemoryConfigStorage = new WxCpInMemoryConfigStorage();
        wxCpInMemoryConfigStorage.setCorpId(SpringUtil.getBean(WiseduConfig.class).getAppId());
        wxCpInMemoryConfigStorage.setCorpSecret(corpSecret);
        return wxCpInMemoryConfigStorage;
    }

}
