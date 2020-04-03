package com.ljqiii.hairlessauth.dao;

import com.ljqiii.hairlesscommon.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    User selectUserById(@Param("id") Integer id);

    User selectUserByUserName(@Param("username") String userName);

    List<SimpleGrantedAuthority> selectAuthoritiesByUserId(@Param("userid") int userid);

    int deleteUser(User user);

    int insertUser(User user);

    @Update("update user set lastlogintime=#{time} where user.username=#{username}")
    int updateLastLoginTime(String username, Date time);

    @Select("select count(*) from user where username=#{username}")
    int countUser(String username);

    @Select("select lastlogintime from user where user.username=#{username}")
    Date selectLastlogintime(String username);}
