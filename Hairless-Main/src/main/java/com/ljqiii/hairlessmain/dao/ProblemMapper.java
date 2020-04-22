package com.ljqiii.hairlessmain.dao;


import com.ljqiii.hairlesscommon.domain.Problem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemMapper {

    List<Problem> selectProblem(@Param("category") String category);

    @Select("select * from problem where id=#{id} limit 1")
    Problem selectProblemById(@Param("id") int id);

    @Delete("delete from problem where id=#{id}")
    int deleteProblemById(@Param("id") int id);

    @Select("select onlyVip from problem where id=#{id}")
    boolean selectIfOnlyVipByProblemId(@Param("id") int id);

    @Select("select count(*) from favorite where problemid=#{problemid} and username=#{username}")
    int selectCountFavorite(@Param("problemid") int problemid, @Param("username") String username);

    @Insert("insert into problem(dockerImage,lang,cmd,description,initCode,complexity,title,onlyVip,dockerCacheDir,memoryLimit,ownerUserName)values (#{dockerImage},#{lang},#{cmd},#{description},#{initCode},#{complexity},#{title},#{onlyVip},#{dockerCacheDir},#{memoryLimit},#{ownerUserName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertProblem(Problem problem);


}
