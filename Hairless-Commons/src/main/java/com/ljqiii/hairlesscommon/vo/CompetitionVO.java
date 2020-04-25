package com.ljqiii.hairlesscommon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionVO {
    int id;
    String title;
    String createUserNickName;
    String startTime;
    String endTime;
    Boolean isPublic;
    String description;
    boolean isUserJoined;//是否已经参加
    String status;//unstart, processing, end
}
