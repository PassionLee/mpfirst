package com.mp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.mp.entity.User;
import com.mp.dao.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FirstApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void select() {
        List<User> list = userMapper.selectList(null);
        Assert.assertEquals(5, list.size());
        list.forEach(System.out::println);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setId(187411232L);
        user.setName("大师傅");
        user.setAge(22);
        user.setEmail("xq@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        user.setRemarks("sssssssssssssssss");
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void selectById() {
        User user = userMapper.selectById(1094590409767661570L);
        System.out.println(user);
    }

    @Test
    public void selectIds() {
        List<Long> ids = Arrays.asList(1094590409767661570L, 1094592041087729666L, 1088250446457389058L);
        List<User> users = userMapper.selectBatchIds(ids);
        users.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        HashMap<String, Object> columnMap = new HashMap<>();
        // map key must column of this table
        columnMap.put("name", "王天风");
        columnMap.put("age", 25);
        List<User> users = userMapper.selectByMap(columnMap);
        users.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE name LIKE ? AND age < ?
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        QueryWrapper<User> query = Wrappers.query();
        queryWrapper.like("name", "雨").lt("age", 35);

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper4() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE date_format(create_time, '%Y-%m-%d')= ? AND manager_id IN (select id from user where name like '王%')
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time, '%Y-%m-%d')= {0}", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '王%' ");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper5() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE name LIKE ? AND ( age < ? OR email IS NOT NULL )
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper6() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE name LIKE ? OR ( age < ? AND age > ? AND email IS NOT NULL )
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").or(wq -> wq.lt("age", 40).gt("age", 20).isNotNull("email"));
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper7() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE ( age < ? OR email IS NOT NULL ) AND name LIKE ?
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.nested(wq -> wq.lt("age", 40).or().isNotNull("email")).likeRight("name", "王");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper8() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE age IN (?,?,?,?)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper9() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE age IN (?,?,?,?)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // last 有SQL注入风险
        queryWrapper.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperSupper() {
        // SELECT id,name FROM user WHERE name LIKE ? AND age < ?
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name").like("name", "雨").lt("age", 35);

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperSupper2() {
        // SELECT id,name,age,manager_id FROM user WHERE name LIKE ? AND age < ?
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(User.class, info -> !info.getColumn().equals("create_time") &&
                !info.getColumn().equals("email"))
                .like("name", "雨").lt("age", 35);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void testCondition() {
        condition("王", null);
    }

    private void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if (!StringUtils.isEmpty(name)) {
//            queryWrapper.like("name", name);
//        }
//        if (!StringUtils.isEmpty(email)) {
//            queryWrapper.like("email", email);
//        }
        queryWrapper.like(!StringUtils.isEmpty(name), "name", name)
                .like(!StringUtils.isEmpty(email), "email", email);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperEntity() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE name=? AND age=? AND name LIKE ? AND age < ?
        User whereUser = new User();
        // @TableField(condition = SqlCondition.LIKE)
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE name LIKE CONCAT('%',?,'%') AND age=? AND name LIKE ? AND age < ?
        whereUser.setName("刘红雨");
        whereUser.setAge(32);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);
        queryWrapper.like("name", "雨").lt("age", 35);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperAllEq() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE name = ? AND age = ?
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> condi = new HashMap<>();
        condi.put("name", "王天风");
        condi.put("age", null);
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE name = ?
//        queryWrapper.allEq(condi, false);
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE age IS NULL
        queryWrapper.allEq((k, v) -> !k.equals("name"), condi);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperMaps() {
        // SELECT id,name,age,manager_id FROM user WHERE name LIKE ? AND age < ?
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name").like("name", "雨").lt("age", 35);

        List<Map<String, Object>> list = userMapper.selectMaps(queryWrapper);
        list.forEach(System.out::println);
    }


    @Test
    public void selectByWrapperMaps2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // SELECT avg(age) avg_age,min(age) min_age,max(age) max_age FROM user GROUP BY manager_id HAVING sum(age)<?
        queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age)<{0}", 500);

        List<Map<String, Object>> list = userMapper.selectMaps(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name").like("name", "雨").lt("age", 35);
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 35);
        Integer integer = userMapper.selectCount(queryWrapper);
        System.out.println(integer);
    }

    @Test
    public void selectByWrapperOne() {
        // Expected one result (or null) to be returned by selectOne(), but found: 2
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 35);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void selectLambda() {
        // SELECT id,name,age,email,manager_id,create_time FROM user WHERE name LIKE ? AND age < ?
//        LambdaQueryWrapper<User> lqw = new QueryWrapper<User>().lambda();
//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.like(User::getName, "雨").lt(User::getAge, 35);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectLambda2() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.likeRight(User::getName, "王").and(lqw -> lqw.lt(User::getAge, 40))
                .or().isNotNull(User::getEmail);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectLambda3() {
        List<User> users = new LambdaQueryChainWrapper<User>(userMapper).like(User::getName, "雨").ge(User::getAge, 20).list();
        users.forEach(System.out::println);
    }

    @Test
    public void selectMy() {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = Wrappers.lambdaQuery();
        userLambdaQueryWrapper.likeRight(User::getName, "王").and(lqw -> lqw.lt(User::getAge, 40));
        List<User> users = userMapper.selectAll(userLambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void selectPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 23);
        Page<User> page = new Page<>(1, 2);
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
        System.out.println("总页数" + iPage.getPages());
        System.out.println("总记录数" + iPage.getTotal());
        List<User> records = iPage.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    public void selectMapPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 23);
        Page<User> page = new Page<>(1, 2, false);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, queryWrapper);
        System.out.println("总页数" + mapIPage.getPages());
        System.out.println("总记录数" + mapIPage.getTotal());
        List<Map<String, Object>> records = mapIPage.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    public void selectMyPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 23);
        Page<User> page = new Page<>(1, 2);
        IPage<User> iPage = userMapper.selectUserPage(page, queryWrapper);
        System.out.println("总页数" + iPage.getPages());
        System.out.println("总记录数" + iPage.getTotal());
        List<User> records = iPage.getRecords();
        records.forEach(System.out::println);
    }
}
