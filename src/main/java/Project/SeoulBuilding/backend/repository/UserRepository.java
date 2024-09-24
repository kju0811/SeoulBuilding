package Project.SeoulBuilding.backend.repository;

import Project.SeoulBuilding.backend.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);


    UserEntity findByUsername(String username);
}
