package com.dgut.medicalsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dgut.medicalsystem.entity.MedicalHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface MedicalHistoryService extends IService<MedicalHistory> {

    /**
     * 通过病人id获取该病人最新的病历信息
     * @param userId 病人Id
     * @return 该病人最新的病历
     */
    MedicalHistory getLatestHistory(Integer userId);

    /**
     * 获取病人的所有病历信息
     * @param userId 病人Id
     * @return 该病人的所有病例信息列表
     */
    IPage<MedicalHistory> getUserAllHistory(Integer userId, Integer page, Integer size);

    Integer getDrByRecord(Long rid,Integer uid);

}
