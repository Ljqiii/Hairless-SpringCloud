package com.ljqiii.hairlesscompetition.dao;

import com.ljqiii.hairlesscommon.domain.Competition;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompetitionMapper {

    @Insert("insert into competition(title,createUserame,startTime,endTime,isPublic,description)values (#{title},#{createUserame},#{startTime},#{endTime},#{isPublic},#{description})")
    int insertCompetition(Competition competition);

    @Delete("delete from competition where id=#{competitionId}")
    int deleteCompetition(@Param("competitionId") int competitionId);

}
