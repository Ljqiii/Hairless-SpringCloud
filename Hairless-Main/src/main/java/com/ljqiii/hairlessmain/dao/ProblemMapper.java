package com.ljqiii.hairlessmain.dao;


import com.ljqiii.hairlesscommon.domain.Problem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper {

    List<Problem> selectProblem(@Param("category") String category);

    @Select("select * from problem where id=#{id} limit 1")
    Problem selectProblemById(@Param("id") int id);

    @Delete("delete from problem where id=#{id}")
    int deleteProblemById(@Param("id") int id);
}
