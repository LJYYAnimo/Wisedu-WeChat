package com.wisedu.wechat.util;

import com.wisedu.wechat.config.WiseduConfig;
import com.wisedu.wechat.dto.SignatureInfoDto;
import com.wisedu.wechat.vo.SignVo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

/**
 * @author 刘金泳
 * @Date 2019/7/23
 */
public class SignUtil {

    public static SignVo sign(String jsapi_ticket, String url) {
        SignVo signVo = new SignVo();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        signVo.setCode(0);
        SignatureInfoDto signatureInfoDto = new SignatureInfoDto();
        signatureInfoDto.setUrl(url);
        signatureInfoDto.setCorpId(SpringUtil.getBean(WiseduConfig.class).getAppId());
        signatureInfoDto.setJsapi_ticket(jsapi_ticket);
        signatureInfoDto.setNonceStr(nonce_str);
        signatureInfoDto.setTimestamp(timestamp);
        signatureInfoDto.setSignature(signature);
        signVo.setData(signatureInfoDto);
        return signVo;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

}
