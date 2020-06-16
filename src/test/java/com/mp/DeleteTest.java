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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author lsl
 * @date 2020/5/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById() {
        int i = userMapper.deleteById(1265938181970046977L);
        System.out.println(i);
    }

    @Test
    public void deleteByMap() {
        HashMap<String, Object> columnMap = new HashMap<>();
        // map key must column of this table
        columnMap.put("name", "象棋");
        columnMap.put("age", 25);
        int i = userMapper.deleteByMap(columnMap);
        System.out.println(i);
    }

    @Test
    public void deleteBatchIds() {
        int i = userMapper.deleteBatchIds(Arrays.asList(1265923939539685377L, 1265922775343558657L));
        System.out.println(i);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        LambdaQueryWrapper<User> gt = wrapper.eq(User::getAge, 27).or().gt(User::getAge, 41);
        int delete = userMapper.delete(gt);
        System.out.println(delete);
    }
}
