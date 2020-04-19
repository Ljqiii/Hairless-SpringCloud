package com.ljqiii.hairlesscommon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentVO {
    int id;
    String userNickName;
    String username;
    String avatarUrl;
    String content;
    String createTime;
    List<PostCommentVO> replys;
}
