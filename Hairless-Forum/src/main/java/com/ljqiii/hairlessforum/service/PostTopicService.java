package com.ljqiii.hairlessforum.service;

import com.ljqiii.hairlesscommon.domain.PostTopic;
import com.ljqiii.hairlessforum.dao.PostTopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTopicService {

    @Autowired
    PostTopicMapper postTopicMapper;

    public boolean addPostTopic(String name) {
        if (postTopicMapper.selecetCountPostTopic(name) >= 1) {
            throw new IllegalArgumentException("话题已存在");
        }
        int i = postTopicMapper.insertPostTopic(PostTopic.builder().name(name).build());
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deletePostTopic(int id) {
        if (postTopicMapper.deletePostTopic(id) >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<PostTopic> listPostTopic() {
        return postTopicMapper.selecetPostTopics();
    }

}
