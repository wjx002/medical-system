package com.dgut.medicalsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 坚宝医疗
 * @since 2021-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通知表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 通知时间
     */
    private LocalDateTime date;

    /**
     * 通知内容
     */
    private String description;

    /**
     * 通知标识   区分内部外部
     */
    private Integer type;

    /**
     * 被通知人（医疗人员和用户）
     */
    private Integer owner;


}
