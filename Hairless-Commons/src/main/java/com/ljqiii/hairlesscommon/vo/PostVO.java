package com.ljqiii.hairlesscommon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostVO {
    int id;
    List<String> topicNames;
    String userNickName;
    String username;
    String title;
    String avatarUrl;
    String content;
    String createTime;
    int postCommentCount;
}
