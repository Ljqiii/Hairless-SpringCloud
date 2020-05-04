package com.ljqiii.hairlesscompetition.dao;

import com.ljqiii.hairlesscommon.domain.Competition;
import com.ljqiii.hairlesscommon.domain.Submit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompetitionSubmitMapper {

    @Insert("insert into competition_submit(competitionId,submitId)values (#{competition.id} ,#{submit.id})")
    int insertCompetitionSubmit(@Param("competition") Competition competition, @Param("submit") Submit submit);

    @Insert("insert into competition_submit(competitionId,submitId)values (#{competition.id} ,#{submitid})")
    int insertCompetitionSubmitBySubmitId(@Param("competition") Competition competition, @Param("submitid") int submitid);

}
