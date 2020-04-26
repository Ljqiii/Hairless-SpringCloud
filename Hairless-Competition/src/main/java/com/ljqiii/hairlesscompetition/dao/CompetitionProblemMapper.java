package com.ljqiii.hairlesscompetition.dao;

import com.ljqiii.hairlesscommon.domain.Competition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompetitionProblemMapper {


    int insertCompetitionProblem(@Param("competition") Competition competition, @Param("problemIds") List<Integer> problemIds);

}
