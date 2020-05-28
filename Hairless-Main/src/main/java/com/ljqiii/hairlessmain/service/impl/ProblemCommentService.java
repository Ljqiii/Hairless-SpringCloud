package com.ljqiii.hairlessmain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.ProblemComment;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlesscommon.vo.PostCommentVO;
import com.ljqiii.hairlessmain.dao.ProblemCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProblemCommentService {

    @Autowired
    ProblemCommentMapper problemCommentMapper;

    public Integer addComment(String userName, Integer postId, Integer commentId, String postCommentContent) {
        ProblemComment problemComment = ProblemComment.builder()
                .createTime(new Date())
                .isDel(false)
                .problemId(postId)
                .replyId(commentId)
                .content(postCommentContent)
                .userName(userName).build();
        int i = problemCommentMapper.insertProblemComment(problemComment);
        return problemComment.getId();
    }

    public boolean removeComment(Integer commentId) {
        int result = problemCommentMapper.deleteProblemComment(commentId);
        return result == 1;
    }

    public PageData<List<PostCommentVO>> listProblemComment(Integer problemId, int pageNum, int pageCount) {
        PageData<List<PostCommentVO>> pageData = new PageData<>();
        Page<PostCommentVO> postVOs = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> problemCommentMapper.selectProblemComment(problemId));
        pageData.setContent(postVOs.getResult());

        pageData.setPageInfo(PageInfo.builder()
                .pageSize(postVOs.getPageSize())
                .pageNum(postVOs.getPageNum())
                .total(postVOs.getTotal())
                .build());
        return pageData;
    }

}
