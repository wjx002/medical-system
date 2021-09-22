package com.dgut.medicalsystem.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Appointment;
import com.dgut.medicalsystem.entity.MedicalStaff;
import com.dgut.medicalsystem.entity.dto.AppointmentInfo;
import com.dgut.medicalsystem.entity.dto.Pagination;
import com.dgut.medicalsystem.entity.dto.Progress;
import com.dgut.medicalsystem.entity.dto.RegistUser;
import com.dgut.medicalsystem.entity.User;
import com.dgut.medicalsystem.service.AppointmentService;
import com.dgut.medicalsystem.service.MedicalStaffService;
import com.dgut.medicalsystem.service.UserService;
import com.google.gson.JsonObject;
import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MedicalStaffService medicalStaffService;

    @Autowired
    AppointmentService appointmentService;

    @RequestMapping("/hello")
    public String hello(HttpServletRequest req) {
        return req.getRemoteHost();
    }



    @RequestMapping("DescribeCaptchaResult")
    public String DescribeCaptchaResult(HttpServletRequest request) {
        String res = "";
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
            req.setCaptchaAppId(2067963886L);
            req.setAppSecretKey("0qslKhofvaeRP66wzKDvjLQ**");
            req.setRandstr(request.getParameter("Randstr"));
            req.setTicket(request.getParameter("Ticket"));
            req.setCaptchaType(9L);
            req.setUserIp(request.getParameter("ip"));



            // 返回的resp是一个DescribeCaptchaResultResponse的实例，与请求对象对应
            DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);
            // 输出json格式的字符串回包
            res = DescribeCaptchaResultResponse.toJsonString(resp);
            return res;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return res;
    }


    @RequestMapping("register")
    public Map<String, String> register(@RequestBody RegistUser user) {
        Map<String, String> map = new HashMap<>();
        if (user.getPassword().isBlank() || user.getConfirm().isBlank() ||
                user.getEmail().isBlank() || user.getTel().isBlank()) {
            map.put("ret", "3");
            map.put("description", "数据非法");
        }
        if (!user.getPassword().equals(user.getConfirm())) {
            map.put("ret", "1");
            map.put("description", "密码不一致");
        } else {
            try {
                User regist_user = new User();
                regist_user.setEmail(user.getEmail());
                regist_user.setTel(user.getTel());
                regist_user.setUsername(user.getTel());
                regist_user.setPassword(SaSecureUtil.md5(user.getPassword()));
                boolean res = userService.save(regist_user);
                if (res) {
                    map.put("ret", "0");
                    map.put("description", "注册成功");
                } else {
                    map.put("ret", "2");
                    map.put("description", "帐号已存在");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();

        if (user.getPassword().isBlank() || user.getUsername().isBlank()) {
            map.put("ret", "2");
            map.put("description", "数据非法");
            return map;
        }
        try {
            //判断登录是否成功
            User res = userService.getByUsername(user);
            if (res != null) {
                res.setPassword(null);
                map.put("ret", "0");
                map.put("user", res);
                //获取该用户角色
                map.put("roles", "Patient");
                map.put("description", "登录成功");
                map.put("tokenName", StpUtil.getTokenInfo().getTokenName());
                map.put("tokenValue", StpUtil.getTokenInfo().getTokenValue());
            } else {
                map.put("ret", "1");
                map.put("description", "帐号或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("ret", "3");
            map.put("description", "系统错误");
        }
        return map;
    }

    @RequestMapping("loginOut")
    public Map<String,String> loginOut(){
        Map<String, String> map = new HashMap<>();
        StpUtil.logout();
        // 获取当前会话是否已经登录，返回true=已登录，false=未登录
        if (StpUtil.isLogin()) {
            map.put("ret", "1");
            map.put("description", "注销失败");
        } else {
            map.put("ret", "0");
            map.put("description", "注销成功");
        }
        return map;
    }

    @SaCheckLogin
    @RequestMapping("/checkInfo")
    public Map<String, Object> checkInfo() {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        try {
            //判断个人信息是否为空
            boolean res = userService.checkInfoById(id);
            if (res) {
                map.put("ret", "0");
                map.put("description", "查找成功");
                map.put("userInfo", userService.getInfoById(id));
            } else {
                map.put("ret", "1");
                map.put("description", "该用户未填写个人信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @SaCheckLogin
    @RequestMapping("updateInformation")
    public Map<String, Object> updateInformation(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        user.setId(id);
        try {
            if (userService.updateInfomation(user)) {
                map.put("ret", "0");
                map.put("description", "更新成功");
            } else {
                map.put("ret", "1");
                map.put("description", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 新增个人信息功能
     * @param user
     * @return
     */
    @SaCheckLogin
    @RequestMapping("addInformation")
    public Map<String, Object> addInformation(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginIdByToken(StpUtil.getTokenValue()).toString().substring(1));
        user.setId(id);
        try {
            if (userService.updateInfomation(user)) {
                map.put("ret", "0");
                map.put("description", "添加成功");
            } else {
                map.put("ret", "1");
                map.put("description", "添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
    * 查看进度
    * */
    @SaCheckLogin
    @RequestMapping("checkProgress")
    public Map<String, Object> checkProgress() {
        Map<String, Object> map = new HashMap<>();
        try {
            Integer id = Integer.valueOf(StpUtil.getLoginId().toString().substring(1));
            Progress progress = userService.checkProgressById(id);

            if (progress != null) {
                User user = userService.getById(id);
                user.setPassword(null);
                user.setIdno(null);

                MedicalStaff doctor = medicalStaffService.getByNewestAppointment(id);

                Appointment appointment = appointmentService.getLatestByUser(id);

                map.put("ret", 0);
                map.put("data", progress);
                map.put("user",user);
                map.put("doctor",doctor);
                map.put("appointment",appointment);
                map.put("msg","获取成功");
            } else {
                map.put("ret", 1);
                map.put("data", null);
                map.put("msg","获取失败");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("ret", 2);
            map.put("data", null);
            map.put("msg","服务出错");
            return map;
        }
    }

    /*
    * 校验密码功能
    * */
    @SaCheckLogin
    @PostMapping("/checkPass")
    public Map<String,Object> checkPass(@RequestBody Map<String,String> body){
        Map<String, Object> map = new HashMap<>();
        String password = body.get("password");
        Integer id = Integer.valueOf(StpUtil.getLoginId().toString().substring(1));
        User user = new User().setId(id).setPassword(password);
        try{
            if (userService.checkPass(user)){
                map.put("ret", "0");
                map.put("description", "校验成功");
            }
            else{
                map.put("ret", "1");
                map.put("description", "校验失败");
            }
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 更新密码
    * */
    @SaCheckLogin
    @PostMapping("/updatePass")
    public Map<String,Object> updatePass(@RequestBody Map<String,String> body){

        Map<String,Object> map = new HashMap<>();
        String oldPass = body.get("oldPass");
        String newPass = body.get("newPass");
        if (oldPass.equals(newPass)){
            map.put("ret",2);
            map.put("msg","两次密码一致!");
            return map;
        }

        Integer id = Integer.valueOf(StpUtil.getLoginId().toString().substring(1));
        User user = new User().setId(id).setPassword(oldPass);
        try{
            if(userService.checkPass(user)){
                user.setPassword(newPass);
                Integer flag = userService.updatePass(user);
                if (flag == 1){
                    map.put("ret",flag);
                    map.put("msg","修改成功");
                }else{
                    map.put("ret",flag);
                    map.put("msg","修改失败");
                }

            }else{
                map.put("ret",-1);
                map.put("msg","原密码错误");
                return map;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }


    /*
    * 更新绑定身份
    * */
    @SaCheckLogin
    @PostMapping("/updateIdNo")
    public Map<String,Object> updateIdNo(@RequestBody Map<String,String> body){
        Map<String, Object> map = new HashMap<>();
        Integer id = Integer.valueOf(StpUtil.getLoginId().toString().substring(1));
        String idNo = body.get("idNo");
        boolean validCard = IdcardUtil.isValidCard(idNo);
        if (!validCard){
            map.put("ret",-3);
            map.put("msg","身份证格式错误");
            return map;
        }

        User user = new User().setId(id).setIdno(idNo);
        try{
            Boolean flag = userService.updateIdNo(user);
            if(flag){
                map.put("ret",1);
                map.put("msg","绑定成功");
            }
            else{
                map.put("ret",-1);
                map.put("msg","绑定失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("ret",-2);
            map.put("msg","绑定错误");
        }
        return map;
    }

}
