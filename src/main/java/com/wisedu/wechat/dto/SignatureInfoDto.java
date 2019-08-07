package com.wisedu.wechat.dto;

import lombok.Data;

/**
 * 微信端JSdk 验证
 * @author 刘金泳
 * @Date 2019/7/23
 */
@Data
public class SignatureInfoDto {

    private String url;

    private String corpId;

    private String jsapi_ticket;

    private String nonceStr;

    private String timestamp;

    private String signature;

}
