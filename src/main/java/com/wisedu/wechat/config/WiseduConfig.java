package com.wisedu.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 刘金泳
 * @Date 2019/7/24
 */
@Component
@Data
@ConfigurationProperties(prefix = "wisedu")
public class WiseduConfig {

    private String type;

    private String appId;

}
