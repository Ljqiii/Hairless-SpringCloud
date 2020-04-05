package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SubmitMapper {

    List<Integer> selectSumbitSuccessProblemId(@Param("problems") List<Problem> problems, @Param("username") String username);

    List<Integer> selectSumbitProblemId(@Param("problems") List<Problem> problems, @Param("username") String username);
}
