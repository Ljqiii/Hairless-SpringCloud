package com.ljqiii.hairlessaccount.dao;

import com.ljqiii.hairlesscommon.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Time;
import java.util.Date;

@Mapper
public interface UserMapper {

    @Update("update user set lastlogintime=#{time} where user.username=#{username}")
    int updateLastLoginTime(String username, Date time);

    @Select("select count(*) from user where username=#{username}")
    int countUser(String username);

    @Select("select lastlogintime from user where user.username=#{username}")
    Date selectLastlogintime(String username);

    @Select("select * from user where user.username=#{username}")
    User selecetUserByUserName(String username);

    @Update("update user set encodedpassword= #{encodedpassword} where username=#{username} ")
    int updateEncodedPassword(@Param("username") String username, @Param("encodedpassword") String encodedpassword);
}
