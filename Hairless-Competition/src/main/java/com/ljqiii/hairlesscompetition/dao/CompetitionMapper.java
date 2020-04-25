package com.ljqiii.hairlesscompetition.dao;

import com.ljqiii.hairlesscommon.domain.Competition;
import com.ljqiii.hairlesscommon.vo.CompetitionVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompetitionMapper {

    @Insert("insert into competition(title,createUserame,startTime,endTime,isPublic,description,encodedPassword,isDel)values (#{title},#{createUserame},#{startTime},#{endTime},#{isPublic},#{description},#{encodedPassword},#{isDel})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCompetition(Competition competition);

    @Delete("delete from competition where id=#{competitionId}")
    int deleteCompetition(@Param("competitionId") int competitionId);

    List<CompetitionVO> selectCompetitionVO(@Param("competitionId") Integer competitionId, @Param("createUserName") String createUserName, @Param("includeDel") Boolean includeDel, @Param("usernameIfJoin") String usernameIfJoin);

    @Select("select * from competition where id=#{competitionId}")
    Competition selecetCompetitionById(@Param("competitionId") Integer competitionId);

}
