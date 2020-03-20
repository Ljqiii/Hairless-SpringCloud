package com.ljqiii.hairlesscommon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    int id;
    String name;
    String description;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
