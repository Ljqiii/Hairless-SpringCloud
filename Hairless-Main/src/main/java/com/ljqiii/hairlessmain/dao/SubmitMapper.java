package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.CorrectLeaderboard;
import com.ljqiii.hairlessmain.dataobject.ProblemAcceptance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubmitMapper {

    //个人提交正确的
    List<Integer> selectSumbitSuccessProblemId(@Param("problems") List<Problem> problems, @Param("username") String username);

    //个人所有提交的
    List<Integer> selectSumbitProblemId(@Param("problems") List<Problem> problems, @Param("username") String username);

    //提交正确率前十
    List<CorrectLeaderboard> selectCorrectLeaderboard();

    //个人提交正确个数 dinstinct
    @Select("select count(distinct problemid) from submit where username=#{username} and result='success'")
    int selectDistinctSuccessCount(String username);

    //个人提交正确个数
    @Select("select count(problemid) from submit where username=#{username} and result='success'")
    int selectSuccessCount(String username);

    //个人提交总数
    @Select("select count(problemid) from submit where username=#{username} ")
    int selectSubmitCount(String username);

    //正确率
    List<ProblemAcceptance> acceptance(@Param("problems") List<Problem> problems);

    //提交总数量
    @Select("select count(*) from submit where problemid=#{problem.id}")
    int selectSumProblemSubmitedCount(@Param("problem") Problem problem);
}
