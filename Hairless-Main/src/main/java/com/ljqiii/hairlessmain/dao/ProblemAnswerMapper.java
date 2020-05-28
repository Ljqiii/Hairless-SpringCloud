package com.ljqiii.hairlessmain.dao;


import com.ljqiii.hairlesscommon.domain.ProblemAnswer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemAnswerMapper {

    @Insert("insert into problem_answer(problemid,username,answercontent)values (#{problemId}  ,#{username} ,#{answercontent} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertProblemAnswer(ProblemAnswer problemAnswer);

    @Delete("delete from problem_answer where id=#{id}")
    int deleteProblemAnswer(ProblemAnswer problemAnswer);

    @Select("select * from problem_answer where problemid=#{problemId}")
    List<ProblemAnswer> selectProblemAnswerByProblemid(@Param("problemId") int problemId);

}
