package com.ljqiii.hairlessforum.controller;

import com.ljqiii.hairlessforum.dao.PostCommentMapper;
import com.ljqiii.hairlessforum.service.PostCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostCommentController {

    @Autowired
    PostCommentService postCommentService;





}
