package com.ljqiii.hairlessforum.controller;

import com.ljqiii.hairlesscommon.domain.PostTopic;
import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlessforum.dao.PostTopicMapper;
import com.ljqiii.hairlessforum.form.AddPostTopicForm;
import com.ljqiii.hairlessforum.form.DeletePostTopicForm;
import com.ljqiii.hairlessforum.service.PostTopicService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class PostTopicController {

    @Autowired
    PostTopicService postTopicService;

    @PostMapping("/addposttopic")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    HairlessResponse<Void> addPostTopic(Principal principal,
                                        AddPostTopicForm addPostTopicForm) {

        String name = addPostTopicForm.getName();
        boolean result = postTopicService.addPostTopic(name);
        HairlessResponse<Void> response = new HairlessResponse<>();
        if (result == true) {
            response.setCodeMsg(ResultEnum.OK);
        } else {
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
        }
        return response;
    }

    @PostMapping("/deleteposttopic")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    HairlessResponse<Void> deletePostTopic(Principal principal,
                                           DeletePostTopicForm deletePostTopicForm) {
        HairlessResponse<Void> response = new HairlessResponse<>();
        boolean result = postTopicService.deletePostTopic(deletePostTopicForm.getPostTopicId());
        if (result == true) {
            response.setCodeMsg(ResultEnum.OK);
        } else {
            response.setCodeMsg(ResultEnum.SERVER_ERROR);
        }
        return response;
    }


    @GetMapping("/posttopiclist")
    HairlessResponse<List<PostTopic>> listPostTopic() {
        HairlessResponse<List<PostTopic>> response = new HairlessResponse<>();
        response.setCodeMsg(ResultEnum.OK);
        response.setData(postTopicService.listPostTopic());
        return response;
    }

}
