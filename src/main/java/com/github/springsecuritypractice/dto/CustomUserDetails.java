package com.github.springsecuritypractice.dto;

import com.github.springsecuritypractice.repository.users.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final UserEntity userData;

    public CustomUserDetails(UserEntity userData) {
        this.userData = userData;
    }

    // 사용자의 권한 리턴(role)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userData.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userData.getPassword();
    }

    @Override
    public String getUsername() {
        return userData.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {
        // UserEntity에 관련된 칼럼을 추가했을 경우 사용가능함
//        return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // UserEntity에 관련된 칼럼을 추가했을 경우 사용가능함
//        return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // UserEntity에 관련된 칼럼을 추가했을 경우 사용가능함
//        return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        // UserEntity에 관련된 칼럼을 추가했을 경우 사용가능함
//        return UserDetails.super.isEnabled();
        return true;
    }
}
