package com.ljqiii.hairlessmain.dao;


import com.ljqiii.hairlesscommon.domain.Problem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GreatMapper {

    @Insert("insert into great(userName,problemId) values (#{username} ,#{problem.id} )")
    int insertGreat(@Param("username") String username, @Param("problem") Problem problem);

    @Delete("delete from  great where userName=#{username} and problemId=#{problem.id} ")
    int deleteGreat(@Param("username") String username, @Param("problem") Problem problem);

}
