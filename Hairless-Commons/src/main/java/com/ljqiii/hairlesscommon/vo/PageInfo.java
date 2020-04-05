package com.ljqiii.hairlesscommon.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageInfo {

    int pageNum;//当前页数
    long total;//总数
    int pageSize;//当前第几页

}
