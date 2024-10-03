package Project.SeoulBuilding.backend.User.dto;

import Project.SeoulBuilding.backend.User.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private User.Role role;
    private String email;
    private String username;
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
