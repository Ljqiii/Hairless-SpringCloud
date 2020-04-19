package com.ljqiii.hairlessforum.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PostCommentVO;
import com.ljqiii.hairlesscommon.vo.PostVO;
import com.ljqiii.hairlessforum.dao.PostCommentMapper;
import com.ljqiii.hairlessforum.form.DeletePostComment;
import com.ljqiii.hairlessforum.form.PushPostComment;
import com.ljqiii.hairlessforum.form.PushPostForm;
import com.ljqiii.hairlessforum.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostCommentController {

    @Autowired
    PostCommentService postCommentService;


    @PostMapping("/puscomment")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<JSONObject> pushPost(Principal principal,
                                          @RequestBody PushPostComment pushPostComment) {

        Integer postId = pushPostComment.getPostId();
        String postCommentContent = pushPostComment.getPostCommentContent();

        Integer postCommentId = postCommentService.addComment(principal.getName(), pushPostComment.getPostId(), null, pushPostComment.getPostCommentContent());

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject responsejson = new JSONObject();

        responsejson.put("commentId", postCommentId);
        response.setData(responsejson);
        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

    @PostMapping("/deletecomment")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<Void> deletePost(Principal principal,
                                      @RequestBody DeletePostComment deletePostComment) {
        boolean result = postCommentService.removeComment(deletePostComment.getPostId());
        //细节不写了，按照删除成功来
        HairlessResponse<Void> response = new HairlessResponse<>();
        JSONObject responsejson = new JSONObject();

        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

    @GetMapping("/comments")
    HairlessResponse<PageData<List<PostCommentVO>>> pushPost(@RequestParam(value = "postId", required = false, defaultValue = "") Integer postId,
                                                             @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                             @RequestParam(value = "pagecount", required = false, defaultValue = "10") int pageCount) {

        HairlessResponse<PageData<List<PostCommentVO>>> response = new HairlessResponse<>();
        PageData<List<PostCommentVO>> listPageData = postCommentService.listPostComment(postId, pageNum, pageCount);
        response.setCodeMsg(ResultEnum.OK);
        response.setData(listPageData);
        return response;
    }

}
