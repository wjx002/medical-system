package com.dgut.medicalsystem.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Notice;
import com.dgut.medicalsystem.entity.dto.Pagination;
import com.dgut.medicalsystem.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @SaCheckLogin
    @RequestMapping("checkNotices")
    public IPage<Notice> checkNotices(@RequestBody Pagination pagination) {
        try {
            Page<Notice> page = new Page<>(pagination.getPage(), pagination.getSize());
            int userid = Integer.parseInt(StpUtil.getLoginId().toString().substring(1));
            return noticeService.page(page, new QueryWrapper<Notice>().eq("owner", userid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
