package com.ljqiii.hairlessforum.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PostVO;
import com.ljqiii.hairlessforum.form.DeletePostForm;
import com.ljqiii.hairlessforum.form.PushPostForm;
import com.ljqiii.hairlessforum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/pushpost")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<JSONObject> pushPost(Principal principal,
                                          @RequestBody PushPostForm pushPostForm) {
        Integer postid = postService.newPost(principal.getName(), pushPostForm.getPostTopic(), pushPostForm.getPostTitle(), pushPostForm.getPostContent());

        HairlessResponse<JSONObject> response = new HairlessResponse<>();
        JSONObject responsejson = new JSONObject();

        responsejson.put("postid", postid);
        response.setData(responsejson);
        response.setCodeMsg(ResultEnum.OK);
        return response;
    }

    @PostMapping("/deletepost")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<Void> deletePost(Principal principal,
                                      @RequestBody DeletePostForm deletePostForm) {
        boolean forcedel = false;
        if (principal instanceof OAuth2Authentication && ((OAuth2Authentication) principal).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            forcedel = true;
        }
        boolean result = postService.deletePost(forcedel, deletePostForm.getPostid(), principal.getName());

        HairlessResponse<Void> response = new HairlessResponse<>();

        if (result == true) {
            response.setCodeMsg(ResultEnum.OK);
        } else {
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/posts")
    HairlessResponse<PageData<List<PostVO>>> posts(Principal principal,
                                                   @RequestParam(value = "username", required = false, defaultValue = "") String username,
                                                   @RequestParam(value = "postId", required = false, defaultValue = "") Integer postId,
                                                   @RequestParam(value = "posttopicid", required = false, defaultValue = "") Integer postTopicId,
                                                   @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount) {


        boolean selecetDeleted = false;
//        if (principal != null && ((OAuth2Authentication) principal).getUserAuthentication().getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()).contains(RoleConstants.Admin)) {
//            selecetDeleted = true;
//        }
        HairlessResponse<PageData<List<PostVO>>> response = new HairlessResponse<>();
        PageData<List<PostVO>> listPageData = postService.listPost(selecetDeleted, postId, postTopicId, username, pageNum, pageCount);

        response.setCodeMsg(ResultEnum.OK);
        response.setData(listPageData);

        return response;
    }
}
