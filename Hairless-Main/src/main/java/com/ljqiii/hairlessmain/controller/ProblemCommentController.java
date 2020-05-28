package com.ljqiii.hairlessmain.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PostCommentVO;
import com.ljqiii.hairlessmain.form.DeleteProblemComment;
import com.ljqiii.hairlessmain.form.PushProblemComment;
import com.ljqiii.hairlessmain.service.impl.ProblemCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ProblemCommentController {

    @Autowired
    ProblemCommentService problemCommentService;


    @PostMapping("/pushProblemComment")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<JSONObject> pushPost(Principal principal,
                                          @RequestBody PushProblemComment pushProblemComment) {

        Integer postId = pushProblemComment.getProblemId();
        String postCommentContent = pushProblemComment.getProblemCommentContent();

        Integer postCommentId = problemCommentService.addComment(principal.getName(), pushProblemComment.getProblemId(), null, pushProblemComment.getProblemCommentContent());

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject responsejson = new JSONObject();

        responsejson.put("commentId", postCommentId);
        response.setData(responsejson);
        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

    @PostMapping("/deleteProblemComment")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<Void> deletePost(Principal principal,
                                      @RequestBody DeleteProblemComment deleteProblemComment) {
        boolean result = problemCommentService.removeComment(deleteProblemComment.getProblemId());
        //细节不写了，按照删除成功来
        HairlessResponse<Void> response = new HairlessResponse<>();
        JSONObject responsejson = new JSONObject();

        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

    @GetMapping("/problemComments")
    HairlessResponse<PageData<List<PostCommentVO>>> pushPost(@RequestParam(value = "problemId", required = false, defaultValue = "") Integer problemId,
                                                             @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                             @RequestParam(value = "pagecount", required = false, defaultValue = "10") int pageCount) {

        HairlessResponse<PageData<List<PostCommentVO>>> response = new HairlessResponse<>();
        PageData<List<PostCommentVO>> listPageData = problemCommentService.listProblemComment(problemId, pageNum, pageCount);
        response.setCodeMsg(ResultEnum.OK);
        response.setData(listPageData);
        return response;
    }

}
