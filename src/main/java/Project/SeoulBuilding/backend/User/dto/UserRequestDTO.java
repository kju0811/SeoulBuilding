package Project.SeoulBuilding.backend.User.dto;

import Project.SeoulBuilding.backend.User.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private User.Role role;

    @NotNull(message = "이메일은 필수 값입니다")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotBlank(message = "이름은 필수 값입니다")
    private String username;

    @NotBlank(message = "비밀번호 값은 필수입니다")
    @Size(min = 6, max = 15, message = "최소 5자리 최대 15자리까지 입력해주세요")
    private String password;

    public User toEntity() {
        return User.builder()
                .role(this.role)
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .build();
    }

}
