package Project.SeoulBuilding.backend.auth.dto;

import Project.SeoulBuilding.backend.auth.entity.Auth;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthresponseDTO {
    private String tokentype;
    private String accessToken;
    private String refreshToken;

    @Builder
    public AuthresponseDTO(Auth entity) {
        this.tokentype = entity.getTokentype();
        this.accessToken = entity.getAccesstoken();
        this.refreshToken = entity.getRefreshtoken();
    }
}
