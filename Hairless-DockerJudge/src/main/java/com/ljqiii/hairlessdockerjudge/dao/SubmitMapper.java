package com.ljqiii.hairlessdockerjudge.dao;


import com.ljqiii.hairlesscommon.domain.Submit;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SubmitMapper {

    @Insert("insert into submit(problemid,username,submitedcode)values(#{problemid},#{username},#{submitedCode})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertSubmit(Submit submit);

    @Update("update submit set result=#{result} where id=#{id}")
    int updateResult(@Param("id") int id, @Param("result") String result);

    @Update("update submit set judgeclient=#{client} where id=#{id}")
    int updateJudgeClient(@Param("id") int id, @Param("client") int client);


}
