package com.dgut.medicalsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dgut.medicalsystem.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 诊断记录表 服务类
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
public interface RecordService extends IService<Record> {
    /**
     * 获取某个病人所有的诊断信息
     * */
    List<Map<String, Object>> getUserAllRecord(Integer userId,Integer page,Integer size);

    Long getUserAllRecordCount(Integer user);

    Integer getDrByRecord(Long rid,Integer uid);

    Integer getAppoByRecord(Long rid);

    Integer getBillByRecord(Long rid);
}
