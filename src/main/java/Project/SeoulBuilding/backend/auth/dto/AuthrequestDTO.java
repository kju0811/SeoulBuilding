package Project.SeoulBuilding.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class AuthrequestDTO {
    private String email;
    private String password;
}
