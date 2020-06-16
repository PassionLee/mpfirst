package com.mp;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lsl
 * @date 2020/5/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1088248166370832385L);
        user.setAge(26);
        user.setEmail("wtf2@baomidou.com");
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    @Test
    public void updateByWrapper() {
        User user = new User();
        user.setAge(29);
        user.setEmail("lyw2@baomidou.com");

        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", "李艺伟").eq("age", 28);

        int update = userMapper.update(user, wrapper);
        System.out.println(update);
    }

    @Test
    public void updateByWrapper1() {
        User user = new User();
        user.setAge(29);
        user.setEmail("lyw2@baomidou.com");

        User whereUser = new User();
        // @TableField(condition = SqlCondition.LIKE)
        // UPDATE user SET age=?, email=? WHERE name LIKE CONCAT('%',?,'%') AND name = ? AND age = ?
        whereUser.setName("李艺伟");
        UpdateWrapper<User> wrapper = new UpdateWrapper<>(whereUser);
        wrapper.eq("name", "李艺伟").eq("age", 28);

        int update = userMapper.update(user, wrapper);
        System.out.println(update);
    }

    @Test
    public void updateByWrapper2() {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", "李艺伟").eq("age", 29)
                .set("age", 30);

        int update = userMapper.update(null, wrapper);
        System.out.println(update);
    }

    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.lambdaUpdate();
        lambdaUpdate.eq(User::getName, "李艺伟").eq(User::getAge, 30)
                .set(User::getAge, 31);

        int row = userMapper.update(null, lambdaUpdate);
        System.out.println(row);
    }

    @Test
    public void updateByWrapperLambdaChain() {
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper)
                .eq(User::getName, "李艺伟").eq(User::getAge, 31)
                .set(User::getAge, 32).update();
        System.out.println(update);
    }
}
