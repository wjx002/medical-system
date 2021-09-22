package com.dgut.medicalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限表	
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysPermision implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限表id	
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限中文
     */
    private String displayName;

    /**
     * 权限级别名称
     */
    private String permisionName;

    /**
     * 地址
     */
    private String url;

    private String type;


}
