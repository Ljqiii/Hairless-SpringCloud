package com.ljqiii.hairlessforum.service;

import com.ljqiii.hairlessforum.dao.PostCommentMapper;
import com.ljqiii.hairlessforum.dao.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostCommentService {

    @Autowired
    PostCommentMapper postCommentMapper;



}
