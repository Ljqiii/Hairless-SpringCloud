package com.ljqiii.hairlesscompetition.dao;

import com.ljqiii.hairlesscommon.domain.Competition;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompetitionUserMapper {


    int insertCompetitionUser(@Param("competition") Competition competition, @Param("usernames") List<String> usernames);

    @Select("select count(0) from competition_user where competitionId=#{competition.id} and username=#{username}")
    int selectCountCompetitionUser(@Param("competition") Competition competition, @Param("username") String username);

}
