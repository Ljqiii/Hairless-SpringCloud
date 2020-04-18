package com.ljqiii.hairlessforum.controller;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.constants.RoleConstants;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PostVO;
import com.ljqiii.hairlessforum.dao.PostMapper;
import com.ljqiii.hairlessforum.form.DeletePostForm;
import com.ljqiii.hairlessforum.form.PushPostForm;
import com.ljqiii.hairlessforum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/pushpost")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<JSONObject> pushPost(Principal principal,
                                          PushPostForm pushPostForm) {
//postService.
        return null;

    }

    @PostMapping("/deletepost")
    @PreAuthorize("hasRole('ROLE_NORMALUSER')")
    HairlessResponse<Void> pushPost(Principal principal,
                                    DeletePostForm deletePostForm) {
        boolean forcedel = false;
        if (principal instanceof OAuth2Authentication && ((OAuth2Authentication) principal).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            forcedel = true;

        }
        boolean result = postService.deletePost(forcedel, deletePostForm.getPostId(), principal.getName());

        HairlessResponse<Void> response = new HairlessResponse<>();

        if (result == true) {
            response.setCodeMsg(ResultEnum.OK);
        } else {
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/posts")
    HairlessResponse<PageData<List<PostVO>>> pushPost(Principal principal,
                                                      @RequestParam(value = "postId", required = false, defaultValue = "") Integer postId,
                                                      @RequestParam(value = "posttopicid", required = false, defaultValue = "") Integer postTopicId,
                                                      @RequestParam(value = "pagenum", required = false, defaultValue = "1") int pageNum,
                                                      @RequestParam(value = "pagecount", required = false, defaultValue = "20") int pageCount) {


        boolean selecetDeleted = false;
        if (principal != null && ((OAuth2Authentication) principal).getUserAuthentication().getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()).contains(RoleConstants.Admin)) {
            selecetDeleted = true;
        }
        HairlessResponse<PageData<List<PostVO>>> response = new HairlessResponse<>();
        PageData<List<PostVO>> listPageData = postService.setProblemVOData(selecetDeleted, postId, postTopicId, pageNum, pageCount);

        response.setCodeMsg(ResultEnum.OK);
        response.setData(listPageData);

        return response;
    }
}
