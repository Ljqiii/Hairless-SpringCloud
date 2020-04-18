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
public class Post {
    int id;
    Integer topicId;
    String userName;
    String content;
    String title;
    Date createTime;
    boolean isDel;
}
