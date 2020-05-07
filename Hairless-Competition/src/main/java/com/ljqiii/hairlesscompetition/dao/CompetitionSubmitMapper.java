package com.ljqiii.hairlesscompetition.dao;

import com.ljqiii.hairlesscommon.domain.Competition;
import com.ljqiii.hairlesscommon.domain.Submit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompetitionSubmitMapper {

    @Insert("insert into competition_submit(competitionId,submitId)values (#{competition.id} ,#{submit.id})")
    int insertCompetitionSubmit(@Param("competition") Competition competition, @Param("submit") Submit submit);

    @Insert("insert into competition_submit(competitionId,submitId)values (#{competition.id} ,#{submitid})")
    int insertCompetitionSubmitBySubmitId(@Param("competition") Competition competition, @Param("submitid") int submitid);

    @Select("select submitId from competition_submit where competitionId=#{competitionId}")
    List<Integer> selectSubmitIdbycompetitionId(@Param("competitionId") Integer competitionId);

    @Select("select submitId from competition_submit,submit where competition_submit.competitionId=submit.id and submit.username=#{username} and competition_submit.competitionId=#{competitionId}")
    List<Integer> selectSubmitIdbycompetitionIdAndUserName(@Param("competitionId") Integer competitionId, @Param("username") String username);
}
