package com.ljqiii.hairlessforum.dao;


import com.ljqiii.hairlesscommon.domain.Post;
import com.ljqiii.hairlesscommon.vo.PostVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Insert("insert into post(topicid,title,username,content,createtime)values(#{topicid},#{title},#{username},#{content},#{createtime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPost(Post post);


    int softDeletePost(@Param("forcedel") boolean forcedel, @Param("postid") int postid, @Param("username") String username);

    List<PostVO> selectPost(@Param("selectDeleted") boolean selectDeleted, @Param("postid") Integer postid, @Param("posttopicid") Integer posttopicid);

}
