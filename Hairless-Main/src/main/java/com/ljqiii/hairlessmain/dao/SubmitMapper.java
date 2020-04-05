package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SubmitMapper {

    //个人提交正确的
    List<Integer> selectSumbitSuccessProblemId(@Param("problems") List<Problem> problems, @Param("username") String username);

    //个人所有提交的
    List<Integer> selectSumbitProblemId(@Param("problems") List<Problem> problems, @Param("username") String username);

    //提交正确率前十
    List<CorrectLeaderboard> selectCorrectLeaderboard();
}
