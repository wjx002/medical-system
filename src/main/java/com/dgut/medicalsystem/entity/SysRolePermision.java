package com.dgut.medicalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色权限中间表
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRolePermision implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限表id
     */
    private Integer permisionId;

    /**
     * 角色表id
     */
    private Integer roleId;


}
