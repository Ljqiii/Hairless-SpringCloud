package com.ljqiii.hairlesscompetition.dao;

import com.ljqiii.hairlesscommon.domain.Competition;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;

@Mapper
public interface CompetitionLeaderBoardMapper {

    @Delete("update competition_leaderboard set isdel=1 where competitionid=#{competition.id}")
    int softDeleteInfoByCompetitionId(@Param("competition") Competition competition);

    @Insert("insert into competition_leaderboard(competitionid,tablecontentjson,tablemetajson)values (#{competition.id},#{tableContentJson},#{tableMetaJson} )")
    int insertInfo(@Param("competition") Competition competition, @Param("tableContentJson") String tableContentJson, @Param("tableMetaJson") String tableMetaJson);

    @Select("select tablecontentjson,tablemetajson from competition_leaderboard where competitionid=#{competition.id}")
    HashMap<String, String> selectLeaderBoardByCompetition(@Param("competition") Competition competition);

}
