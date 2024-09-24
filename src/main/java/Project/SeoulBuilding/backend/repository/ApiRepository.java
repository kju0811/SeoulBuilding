package Project.SeoulBuilding.backend.repository;

import Project.SeoulBuilding.backend.Entity.ApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository <ApiEntity,String> {
}
