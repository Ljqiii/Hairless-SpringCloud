package com.ljqiii.hairlessforum.dao;


import com.ljqiii.hairlesscommon.domain.Post;
import com.ljqiii.hairlesscommon.domain.PostComment;
import com.ljqiii.hairlesscommon.vo.PostCommentVO;
import com.ljqiii.hairlesscommon.vo.PostVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostCommentMapper {

    @Insert("insert into postcomment(replyid,username,postid,content)values(#{replyId},#{userName},#{postId},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPostComment(PostComment postComment);

    @Update("update postcomment set isdel=true where postcomment.id=#{id}")
    int softDeletePostComment(PostComment postComment);

    @Delete("delete from postcomment where id=#{postCommentId}")
    int deletePostComment(@Param("postCommentId") Integer postCommentId);

    List<PostCommentVO> selectPostComment(@Param("postId") Integer postId);


}
