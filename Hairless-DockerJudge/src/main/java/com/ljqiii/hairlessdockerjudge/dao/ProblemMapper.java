package com.ljqiii.hairlessdockerjudge.dao;


import com.ljqiii.hairlesscommon.domain.Problem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface ProblemMapper {

    @Select("select * from problem where id=#{id} limit 1")
    Problem selectProblemById(@Param("id") int id);

    @Select("select onlyVip from problem where id=#{id}")
    boolean selectIfOnlyVipByProblemId(@Param("id") int id);

}
