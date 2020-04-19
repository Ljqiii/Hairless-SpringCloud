package com.ljqiii.hairlessforum.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.PostComment;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlesscommon.vo.PostCommentVO;
import com.ljqiii.hairlesscommon.vo.PostVO;
import com.ljqiii.hairlessforum.dao.PostCommentMapper;
import com.ljqiii.hairlessforum.dao.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostCommentService {

    @Autowired
    PostCommentMapper postCommentMapper;

    public Integer addComment(String userName, Integer postId, Integer commentId, String postCommentContent) {
        PostComment postComment = PostComment.builder()
                .createTime(new Date())
                .isDel(false)
                .postId(postId)
                .replyId(commentId)
                .content(postCommentContent)
                .userName(userName).build();
        int i = postCommentMapper.insertPostComment(postComment);
        return postComment.getId();
    }

    public boolean removeComment(Integer commentId) {
        int result = postCommentMapper.deletePostComment(commentId);
        return result == 1;
    }

    public PageData<List<PostCommentVO>> listPostComment(Integer postId, int pageNum, int pageCount) {
        PageData<List<PostCommentVO>> pageData = new PageData<>();
        Page<PostCommentVO> postVOs = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> postCommentMapper.selectPostComment(postId));
        pageData.setContent(postVOs.getResult());

        pageData.setPageInfo(PageInfo.builder()
                .pageSize(postVOs.getPageSize())
                .pageNum(postVOs.getPageNum())
                .total(postVOs.getTotal())
                .build());
        return pageData;
    }


}
