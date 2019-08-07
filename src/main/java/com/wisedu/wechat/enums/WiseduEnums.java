package com.wisedu.wechat.enums;

import lombok.Getter;

/**
 * @author 刘金泳
 * @Date 2019/7/26
 */
@Getter
public enum  WiseduEnums {

    /**
     * 公众号
     */
    MP("mp","公众号"),
    /**
     * 企业号
     */
    CP("cp","企业号"),;

    private String code;

    private String desc;

    WiseduEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
