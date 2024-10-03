package Project.SeoulBuilding.backend.User.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;

@Getter
public class UserPrincipal implements UserDetails {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.user = user;
        //해당 사용자의 역할(Role) 정보를 가져와 권한으로 변환하는 부분
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(String.valueOf(user.getRole())));
    }
/*
 UserEntity와 관련된 사용자 정보를 저장하고, 사용자의 권한, 추가 속성 및 사용자 이름 속성 키를 초기화
 Spring Security에서 인증 및 권한 부여를 처리하는 데 사용
 */
    public UserPrincipal(User user, Map<String, Object> attributes, String nameAttributeKey) {
        this.user = user;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey()));
    }

    //userdetails 메서드 구현
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    //계정 만료 여부 반환 true:만료되지 않음
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠금 여부 반환 true:잠금되지 않음
    @Override
    public boolean isAccountNonLocked() {
        return user.isNonLock();
    }

    //패스워드 만료 여부 반환 true:만료되지 않음
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 활성화 여부 반환 true:사용가능
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
