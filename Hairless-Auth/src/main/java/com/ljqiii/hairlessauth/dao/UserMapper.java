package com.ljqiii.hairlessauth.dao;

import com.ljqiii.hairlesscommon.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Mapper
public interface UserMapper {

    User selectUserById(@Param("id") Integer id);

    User selectUserByUserName(@Param("username") String userName);

    List<SimpleGrantedAuthority> selectAuthoritiesByUserId(@Param("userid") int userid);

    int deleteUser(User user);

    int insertUser(User user);

}
