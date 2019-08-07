package com.wisedu.wechat.controller;

import com.wisedu.wechat.config.WiseduConfig;
import com.wisedu.wechat.constant.WiseduConstant;
import com.wisedu.wechat.dto.HttpClientDto;
import com.wisedu.wechat.enums.WiseduEnums;
import com.wisedu.wechat.util.GsonUtil;
import com.wisedu.wechat.util.HttpClientUtil;
import com.wisedu.wechat.util.WiseduUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.mp.api.WxMpService;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 刘金泳
 * @Date 2019/7/23
 */
@RestController
@RequestMapping("/wisedu/")
public class WiseduController {

    @Autowired
    private WiseduConfig wiseduConfig;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxCpService wxCpService;

    /**
     * 保存图片
     * @param request
     * @return
     */
    @GetMapping("saveFileFromWeChat")
    public Map<String, Object> saveFileFromWeChat(HttpServletRequest request){

        //-----------------------WisEdu传递过来的参数-------------------------
        String fileToken = request.getParameter("token");
        String serverIds = request.getParameter("serverIds");
        String emapPrefixPath = request.getParameter("emapPrefixPath");
        String scope = request.getParameter("scope");
        String storeId = request.getParameter("storeid");
        //-----------------------WisEdu传递过来的参数-------------------------

        //-----------------------封装成Map-------------------------
        Map<String,Object> map = new HashMap<>(16);
        map.put("url",emapPrefixPath + WiseduConstant.UPLOADTEMPFILE);
        map.put("scope",scope);
        map.put("fileToken",fileToken);
        map.put("storeId",storeId);
        //-----------------------封装成Map-------------------------

        //获取ServerId 用户选择了多少张图片
        String[] strings = serverIds.split(",");

        //长度提取公共参数
        int length = strings.length;

        //判断MP还是CP
        String type = wiseduConfig.getType();

        //上传临时文件结果集
        Map<String,Object> result;

        Map<String,Integer> orderMap = new HashMap<>(16);

        for(int i = 0;i<length;i++){
            File file = null;
            try {
                if(type.equals(WiseduEnums.CP.getCode())){
                    long time1=System.currentTimeMillis();
                    file = wxCpService.getMediaService().download(strings[i]);
                    long time2=System.currentTimeMillis();
                    System.out.println("当前企业微信获取文件耗时："+(time2-time1)+"ms");
                }else if(type.equals(WiseduEnums.MP.getCode())){
                    long time1=System.currentTimeMillis();
                    file = wxMpService.getMaterialService().mediaDownload(strings[i]);
                    long time2=System.currentTimeMillis();
                    System.out.println("当前公众号获取文件耗时："+(time2-time1)+"ms");
                }
            }catch (WxErrorException e){
                e.printStackTrace();
            }
            map.put("bhFile",file);
            long time1=System.currentTimeMillis();
            result = WiseduUtil.uploadTempFile(map);
            long time2=System.currentTimeMillis();
            System.out.println("当前uploadTempFile耗时："+(time2-time1)+"ms");
            if((boolean)result.get("success")){
                orderMap.put(result.get("id").toString(),i);
            }
        }

        Map<String,String> attachmentParamMap = new HashMap<>(16);
        Map<String,Object> attachmentParam = new LinkedHashMap<>();
        attachmentParam.put("storeId",storeId);
        attachmentParam.put("scope",scope);
        attachmentParam.put("fileToken",fileToken);
        attachmentParam.put("orderMap",orderMap);
        attachmentParamMap.put("attachmentParam",GsonUtil.mapToJson(attachmentParam));

        try {
            String saveAttachmentUrl = String.format(WiseduConstant.SAVEATTACHMENT,scope,fileToken);

            long time3=System.currentTimeMillis();
            HttpClientDto httpClientDto = HttpClientUtil.doPost(emapPrefixPath + saveAttachmentUrl ,attachmentParamMap);
            long time4=System.currentTimeMillis();
            System.out.println("当前saveAttachment耗时："+(time4-time3)+"ms");

            return GsonUtil.JsonToMap(httpClientDto.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

