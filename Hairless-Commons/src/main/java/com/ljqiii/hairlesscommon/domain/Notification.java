package com.ljqiii.hairlesscommon.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notification {
    int id;
    int userid;
    String title;
    String content;
    boolean isRead;
    boolean isPush;
}
