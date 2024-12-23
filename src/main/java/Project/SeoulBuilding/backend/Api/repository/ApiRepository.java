package Project.SeoulBuilding.backend.Api.repository;

import Project.SeoulBuilding.backend.Api.entity.ApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApiRepository extends JpaRepository <ApiEntity,String> {
    @Query("SELECT b FROM ApiEntity b WHERE b.FCLT_NM LIKE %:building% ")
    List<ApiEntity> findBuilding(@Param("building") String building);

    public List<ApiEntity> findAll();
}
