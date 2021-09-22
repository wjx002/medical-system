package com.dgut.medicalsystem.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/*
* 今日名医
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class HotDoctor {

    @TableId(value = "docId")
    Integer docId;
    String name;
    String subject;
    String proTitle;
    String roomNo;
    Integer amount;
    String avatar;
}
