package com.ljqiii.hairlesscommon.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageData<T> {
    T content;
    PageInfo pageInfo;
}
