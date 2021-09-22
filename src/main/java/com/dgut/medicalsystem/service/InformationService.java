package com.dgut.medicalsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dgut.medicalsystem.entity.Information;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface InformationService extends IService<Information> {

    boolean addInformation(String information);

    IPage<Information> searchInformation(Integer page, Integer size);

    boolean updateInformation(Integer id, String info);

    boolean deleteInformation(Integer id);
}
