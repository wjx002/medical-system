package com.dgut.medicalsystem.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Information implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资讯表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 消息内容
     */
    private String message;


}
