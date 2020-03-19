package com.ljqiii.hairlessauth.dao;

import com.ljqiii.hairlesscommon.domain.Role;
import com.ljqiii.hairlesscommon.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);

    @Select("select * from roles where id=#{id}")
    Role selectRoleById(@Param("id") int id);

    @Select("select * from roles where name=#{name}")
    Role selectRoleByName(@Param("name") String name);

}