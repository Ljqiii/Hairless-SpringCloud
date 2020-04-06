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
public class FavoriteFolder {
    Integer id;
    String userName;
    String name;
    Date createtime;
    Boolean isPublic;
}
