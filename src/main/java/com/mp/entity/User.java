package com.mp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author lsl
 * @date 2020/5/28
 */
@Data
//@TableName("mp_user")
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    private static final long serialVersionUID = 4121211347864510321L;

//    @TableId
    // 数据库自增
    @TableId(type = IdType.NONE)
    private Long id;

//    @TableField("userName")
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDateTime createTime;

    @TableField(exist = false)
    private String remarks;
}
