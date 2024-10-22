package Project.SeoulBuilding.backend.auth.repository;

import Project.SeoulBuilding.backend.User.domain.User;
import Project.SeoulBuilding.backend.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean existsByUser(User user);

    Optional<Auth> findByRefreshToken(String refreshtoken);


}
