package com.wisedu.wechat.controller;

import com.wisedu.wechat.config.WiseduConfig;
import com.wisedu.wechat.cp.component.CpComponent;
import com.wisedu.wechat.enums.WiseduEnums;
import com.wisedu.wechat.mp.component.MpComponent;
import com.wisedu.wechat.util.SignUtil;
import com.wisedu.wechat.vo.SignVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
/**
 * @author 刘金泳
 * @Date 2019/7/22
 */
@RestController
public class CertificationController {

    @Autowired
    private CpComponent cpComponent;

    @Autowired
    private MpComponent mpComponent;

    @Autowired
    private WiseduConfig wiseduConfig;

    /**
     * Get 请求  包含URl corp
     *
     * 包含双端
     *
     * Js sdk 签名
     * @param request
     * @return
     */
    @GetMapping("checkJSdk")
    public SignVo checkJSdk(HttpServletRequest request){
        String url = request.getParameter("url");
        String corp = request.getParameter("corp");
        SignVo signVo = null;
        if(wiseduConfig.getType().equals(WiseduEnums.CP.getCode())){
            signVo = SignUtil.sign(cpComponent.getTicket(corp),url);
        }else if(wiseduConfig.getType().equals(WiseduEnums.MP.getCode())){
            signVo = SignUtil.sign(mpComponent.getTicket(corp),url);
        }
        return signVo;
    }

}
