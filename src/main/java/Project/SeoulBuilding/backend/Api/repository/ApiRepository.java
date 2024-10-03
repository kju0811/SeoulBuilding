package Project.SeoulBuilding.backend.Api.repository;

import Project.SeoulBuilding.backend.Api.entity.ApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository <ApiEntity,String> {
}
