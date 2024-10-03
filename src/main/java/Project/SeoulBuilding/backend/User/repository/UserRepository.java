package Project.SeoulBuilding.backend.User.repository;

import Project.SeoulBuilding.backend.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    boolean existsByUsername(String email);

    // 사용자 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 사용자 ID로 사용자 찾기
    Optional<User> findById(Long id);

    // 사용자 이름으로 사용자 찾기
    Optional<User> findByUsername(String username);
}
