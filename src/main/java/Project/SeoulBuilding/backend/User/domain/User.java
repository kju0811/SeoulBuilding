package Project.SeoulBuilding.backend.User.domain;

import Project.SeoulBuilding.backend.User.BaseTime;
import Project.SeoulBuilding.backend.User.dto.UserRequestDTO;
import Project.SeoulBuilding.backend.auth.entity.Auth;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Data
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name = "username", unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled;

    @Column(name = "nonLock", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean nonLock;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Auth auth;

    public void update(UserRequestDTO responseDTO) {
        @NotNull(message = "이메일은 필수값입니다.")
        @Email(message = "이메일의 형식이 올바르지 않습니다")
        String email;
        @NotBlank(message = "이름은 필수값입니다.")
        String username;
    }


    /*
    권한 열거형 클래스로 지정
     */
    @Getter
    @RequiredArgsConstructor
    public enum Role {
        NOT_REGISTERED("ROLE_NOT_REGISTERED", "회원가입 이전 사용자"),
        USER("ROLE_USER", "일반 사용자"),
        ADMIN("ROLE_ADMIN", "관리자");

        private final  String value;
        private final String Key;

    }

}
