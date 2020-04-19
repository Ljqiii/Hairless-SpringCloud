package com.ljqiii.hairlessforum.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ljqiii.hairlesscommon.domain.Post;
import com.ljqiii.hairlesscommon.domain.PostTopic;
import com.ljqiii.hairlesscommon.domain.Problem;
import com.ljqiii.hairlesscommon.vo.PageData;
import com.ljqiii.hairlesscommon.vo.PageInfo;
import com.ljqiii.hairlesscommon.vo.PostVO;
import com.ljqiii.hairlesscommon.vo.ProblemListVO;
import com.ljqiii.hairlessforum.dao.PostMapper;
import com.ljqiii.hairlessforum.dao.PostTopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostMapper postMapper;

    @Autowired
    PostTopicMapper postTopicMapper;

    public boolean deletePost(boolean forceDelete, int postid, String username) {
        return postMapper.softDeletePost(forceDelete, postid, username) >= 1;
    }

    /**
     * @param selectDeleted 是否选择已经删除的
     * @param postId        如果有值选择一个，没有选择一堆
     * @param pageNum
     * @param pageCount
     * @return
     */
    public PageData<List<PostVO>> listPost(boolean selectDeleted, Integer postId, Integer postTopicId, int pageNum, int pageCount) {
        PageData<List<PostVO>> pageData = new PageData<>();
        Page<PostVO> postVOs = PageHelper.startPage(pageNum, pageCount)
                .doSelectPage(() -> postMapper.selectPost(selectDeleted, postId, postTopicId));
        pageData.setContent(postVOs.getResult());

        pageData.setPageInfo(PageInfo.builder()
                .pageSize(postVOs.getPageSize())
                .pageNum(postVOs.getPageNum())
                .total(postVOs.getTotal())
                .build());
        return pageData;
    }

    public Integer newPost(String username, Integer postTopic, String title, String content) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .isDel(false)
                .topicId(postTopic)
                .createTime(new Date())
                .userName(username)
                .build();
        int i = postMapper.insertPost(post);

        if (postTopic != null) {
            postTopicMapper.insertPost_Topic(post.getId(), postTopic);
        }

        int id = post.getId();
        return id;
    }


}
