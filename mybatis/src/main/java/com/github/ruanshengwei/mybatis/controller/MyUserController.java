package com.github.ruanshengwei.mybatis.controller;


import com.github.ruanshengwei.mybatis.dao.MyUserMapper;
import com.github.ruanshengwei.mybatis.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("myuser")
public class MyUserController {
    @Autowired
    private MyUserMapper myUserMapper;

    @GetMapping("get")
    public MyUser get(@RequestParam("id")Integer id){

        Map map = new HashMap<>();
        map.put("id",2);
//
        Optional<MyUser> myUser = myUserMapper.selectOptional(2);

        return myUser.get();

//        Optional<MyUser> myUser = myUserMapper.selectByPrimaryKey(2);
//
//        return myUser.get();

    }
}
