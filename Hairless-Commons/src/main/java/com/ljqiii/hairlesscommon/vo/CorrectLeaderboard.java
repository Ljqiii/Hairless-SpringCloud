package com.ljqiii.hairlesscommon.vo;

import com.ljqiii.hairlesscommon.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorrectLeaderboard {
    String username;
    String avatar;
    int successCount;
}
