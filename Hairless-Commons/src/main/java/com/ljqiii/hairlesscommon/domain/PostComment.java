package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostComment {
    int id;
    int postId;
    Integer replyId;//对哪个回复进行回复
    String userName;
    Date createTime;
    boolean isDel;

}
