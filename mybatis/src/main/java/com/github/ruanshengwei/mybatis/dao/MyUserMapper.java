package com.github.ruanshengwei.mybatis.dao;

import com.github.ruanshengwei.mybatis.entity.MyUser;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Optional;

public interface MyUserMapper extends BaseMapper<MyUser> {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(MyUser record);
//
//    int insertSelective(MyUser record);
//
//    Optional<MyUser> selectByPrimaryKey(Integer id);
//
    @Select("select * from myuser where id = #{id}")
    Optional<MyUser> selectById(Integer id);
//
//    int updateByPrimaryKeySelective(MyUser record);
//
//    int updateByPrimaryKey(MyUser record);
}