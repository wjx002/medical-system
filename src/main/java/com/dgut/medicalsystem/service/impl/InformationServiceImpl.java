package com.dgut.medicalsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Information;
import com.dgut.medicalsystem.mapper.InformationMapper;
import com.dgut.medicalsystem.service.InformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements InformationService {

    @Autowired
    InformationMapper informationMapper;

    @Override
    public boolean addInformation(String information) {
        Information info = new Information(null, LocalDateTime.now(),information);
        return this.save(info);
    }

    @Override
    public IPage<Information> searchInformation(Integer page, Integer size) {
        Page<Information> informationPage = new Page<>(page,size);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("time");
        return informationMapper.selectPage(informationPage,wrapper);
    }

    @Override
    public boolean updateInformation(Integer id, String info) {
        Information information = new Information(id,LocalDateTime.now(),info);
        return this.updateById(information);
    }

    @Override
    public boolean deleteInformation(Integer id) {
        return this.removeById(id);
    }
}
