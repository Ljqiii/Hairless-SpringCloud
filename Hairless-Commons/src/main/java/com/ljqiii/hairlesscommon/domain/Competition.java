package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Competition {
    Integer id;
    String title;
    String createUsername;
    Date startTime;
    Date endTime;
    Boolean isPublic;
    String description;
    boolean isDel;
    String encodedPassword;
}
