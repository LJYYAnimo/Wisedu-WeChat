package com.wisedu.wechat.vo;

import com.wisedu.wechat.dto.SignatureInfoDto;
import lombok.Data;

/**
 * @author 刘金泳
 * @Date 2019/7/23
 */
@Data
public class SignVo {

    private Integer code;

    private SignatureInfoDto data;

}
