package Project.SeoulBuilding.backend.User.domain;

import Project.SeoulBuilding.backend.User.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode (callSuper = true)
@Builder
@Entity
@Table( name = "token")
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class Auth extends BaseTime {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

    @Column(name = "accesstoken",nullable = false)
    private String accesstoken;

    @Column(name = "refreshtoken",nullable = false)
    private String refreshtoken;

    @Column(name = "userId",nullable = false)
    private Long userId;

    @Column(name = "expiresAt", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
