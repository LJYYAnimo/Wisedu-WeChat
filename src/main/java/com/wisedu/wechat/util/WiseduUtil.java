package com.wisedu.wechat.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.List;

/**
 * @author 刘金泳
 * @Date 2019/7/24
 */
public class WiseduUtil {

    /**
     * 单文件上传专用
     * @param map
     * @return
     */
    public static Map<String,Object> uploadTempFile(Map<String,Object> map){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(map.get("url").toString());

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addTextBody("scope", map.get("scope").toString(), ContentType.TEXT_PLAIN);
        builder.addTextBody("fileToken",map.get("fileToken").toString(),ContentType.TEXT_PLAIN);
        builder.addTextBody("storeId",map.get("storeId").toString(),ContentType.TEXT_PLAIN);

        File f = (File)map.get("bhFile");

        // 把文件加到HTTP的post请求中
        try {
            builder.addBinaryBody(
                    "bhFile",
                    new FileInputStream(f),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getName()
            );
            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();
            String sResponse= EntityUtils.toString(responseEntity, "UTF-8");
            return GsonUtil.JsonToMap(sResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map<String,Object> uploadAllTempFile(Map<String,Object> map){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(map.get("url").toString());

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        builder.addTextBody("scope", map.get("scope").toString(), ContentType.TEXT_PLAIN);
        builder.addTextBody("fileToken",map.get("fileToken").toString(),ContentType.TEXT_PLAIN);
        builder.addTextBody("storeId",map.get("storeId").toString(),ContentType.TEXT_PLAIN);

        File[] files = (File[])map.get("files");

        // 把文件加到HTTP的post请求中
        try {
            for(File f : files){
                builder.addBinaryBody(
                        "files",
                        new FileInputStream(f),
                        ContentType.APPLICATION_OCTET_STREAM,
                        f.getName()
                );
            }
            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();
            String sResponse= EntityUtils.toString(responseEntity, "UTF-8");
            return GsonUtil.JsonToMap(sResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
