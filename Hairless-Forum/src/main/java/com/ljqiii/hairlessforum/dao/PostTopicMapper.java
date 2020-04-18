package com.ljqiii.hairlessforum.dao;


import com.ljqiii.hairlesscommon.domain.Post;
import com.ljqiii.hairlesscommon.domain.PostTopic;
import com.ljqiii.hairlesscommon.vo.PostVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostTopicMapper {
    @Insert("insert into posttopic(name)values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPostTopic(PostTopic postTopic);

    @Delete("delete from posttopic where id=#{id}")
    int deletePostTopic(@Param("id") int id);

    @Select("select * from posttopic")
    List<PostTopic> selecetPostTopics();

    @Select("select * from posttopic where name=#{name}")
    PostTopic selectPostTopicByName(@Param("name") String name);

    @Select("select count(*) from posttopic where name=#{name}")
    int selecetCountPostTopic(@Param("name") String name);
}
