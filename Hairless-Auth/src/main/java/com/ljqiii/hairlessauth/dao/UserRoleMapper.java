package com.ljqiii.hairlessauth.dao;

import com.ljqiii.hairlesscommon.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Mapper
public interface UserRoleMapper {
    int insert(@Param("userid")int userid,@Param("roleid")int roleid);

    @Select("select role.* from role where id=#{id}")
    Role selectRoleById(@Param("id") int id);

    @Select("select role.* from role where name=#{name}")
    Role selectRoleByName(@Param("name") String name);

}