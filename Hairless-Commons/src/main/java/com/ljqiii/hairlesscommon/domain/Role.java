package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    int id;
    String name;
    String description;

}
