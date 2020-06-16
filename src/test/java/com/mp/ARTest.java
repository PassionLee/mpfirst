package com.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author lsl
 * @date 2020/5/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ARTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * @EqualsAndHashCode(callSuper = false)
     * public class User extends Model<User> {
     *
     * }
     */
    @Test
    public void insert() {
        User user = new User();
        user.setName("圣诞节");
        user.setAge(22);
        user.setEmail("xsdf@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        user.setRemarks("sssssssssssssssss");
        boolean insert = user.insert();
        System.out.println(insert);
    }

    @Test
    public void selectById() {
        User user = new User();
        User selectById = user.selectById(1088248166370832385L);
        System.out.println(user == selectById);
        System.out.println(selectById);
    }

    @Test
    public void selectById2() {
        User user = new User();
        user.setId(1088248166370832385L);

        User selectById = user.selectById();
        System.out.println(user == selectById);
        System.out.println(selectById);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setName("王天风");

        boolean b = user.updateById();
        System.out.println(b);
    }

    @Test
    public void deleteById() {

        User user = new User();
        user.setId(1265990936306769922L);

        boolean b = user.deleteById();
        System.out.println(b);
    }

    @Test
    public void insertOrUpdate() {
        User user = new User();
        user.setId(1265990936306769922L);
        user.setName("网版");
        user.setAge(22);
        user.setEmail("xsdf@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insert = user.insertOrUpdate();
        System.out.println(insert);
    }
}
