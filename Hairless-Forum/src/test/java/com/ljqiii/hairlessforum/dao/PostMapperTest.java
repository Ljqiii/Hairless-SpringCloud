package com.ljqiii.hairlessforum.dao;

import com.ljqiii.hairlesscommon.vo.PostVO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostMapperTest {

    @Autowired
    PostMapper postMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void insertPost() {
    }

    @Test
    void softDeletePost() {
    }

    @Test
    void selectPost() {
        List<PostVO> postVOS = postMapper.selectPost(false,1,1);
        Assert.assertNotNull(postVOS);
    }
}
