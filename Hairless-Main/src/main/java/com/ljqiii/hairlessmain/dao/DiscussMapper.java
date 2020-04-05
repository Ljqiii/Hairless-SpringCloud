package com.ljqiii.hairlessmain.dao;

import com.ljqiii.hairlesscommon.domain.Discuss;
import com.ljqiii.hairlesscommon.domain.Problem;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface DiscussMapper {


    List<Map<Long, Long>> selectCountDiscuss(@Param("problems") List<Problem> problems);

    @Insert("insert into discuss(toid,problemid,username,content),")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Discuss insertDiscuss(Discuss discuss);

    @Delete("delete from discuss where id=#{id}")
    int deleteDiscuss(Discuss discuss);


}
