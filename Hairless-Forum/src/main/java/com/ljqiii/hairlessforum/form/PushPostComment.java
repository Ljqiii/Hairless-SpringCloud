package com.ljqiii.hairlessforum.form;

import lombok.Data;

@Data
public class PushPostComment {
    Integer postId;
    Integer commentId;
    String postCommentContent;
}
