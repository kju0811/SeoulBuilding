package Project.SeoulBuilding.backend.User.dto;

import Project.SeoulBuilding.backend.User.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String email;
    private String username;
    private User.Role role;


    public UserResponseDTO(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.id = user.getId();
        this.role = user.getRole();
    }
}
