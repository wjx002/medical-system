package com.dgut.medicalsystem;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.*;

public class DescribeCaptchaResult {
    public static void main(String[] args) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("AKIDnPltopbH6i4B5qwFBVaPyKSKwlUyeBvK", "KPfoiMktbwCbmkRsRmz8Qjna89mEa3bu");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("captcha.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            CaptchaClient client = new CaptchaClient(cred, "", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeCaptchaResultRequest req = new DescribeCaptchaResultRequest();


            // 返回的resp是一个DescribeCaptchaResultResponse的实例，与请求对象对应
            DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);
            // 输出json格式的字符串回包
            System.out.println(DescribeCaptchaResultResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}
