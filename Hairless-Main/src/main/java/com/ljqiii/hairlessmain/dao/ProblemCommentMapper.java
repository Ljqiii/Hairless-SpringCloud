package com.ljqiii.hairlessmain.dao;


import com.ljqiii.hairlesscommon.domain.PostComment;
import com.ljqiii.hairlesscommon.domain.ProblemComment;
import com.ljqiii.hairlesscommon.vo.ProblemCommentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProblemCommentMapper {

    @Insert("insert into problemcomment(replyid,username,problemid,content)values(#{replyId},#{userName},#{problemId},#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertProblemComment(ProblemComment postComment);

    @Update("update problemcomment set isdel=true where postcomment.id=#{id}")
    int softDeleteProblemComment(PostComment postComment);

    @Delete("delete from problemcomment where id=#{postCommentId}")
    int deleteProblemComment(@Param("postCommentId") Integer postCommentId);

    List<ProblemCommentVO> selectProblemComment(@Param("problemId") Integer problemId);


}
