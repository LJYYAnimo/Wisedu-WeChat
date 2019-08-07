package com.wisedu.wechat.dto;

import lombok.Data;

/**
 * @author 刘金泳
 * @Date 2019/7/25
 */
@Data
public class HttpClientDto {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientDto() {
    }

    public HttpClientDto(int code) {
        this.code = code;
    }

    public HttpClientDto(int code, String content) {
        this.code = code;
        this.content = content;
    }
}
