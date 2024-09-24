package Project.SeoulBuilding.backend.login.repository;

import Project.SeoulBuilding.backend.login.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);


    UserEntity findByUsername(String username);
}
