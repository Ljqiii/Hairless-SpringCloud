package com.ljqiii.hairlesscommon.vo;

import lombok.Data;

@Data
public class CategoryVO {
    int id;
    String symbol;
    String name;

    Integer problemcount;//题目数量
}
