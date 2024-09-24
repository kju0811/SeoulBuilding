package Project.SeoulBuilding.backend.sevice;

import Project.SeoulBuilding.backend.Entity.ApiEntity;
import Project.SeoulBuilding.backend.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    private final ApiRepository apiRepository;

    @Autowired
    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }
    public ApiEntity save(ApiEntity apiEntity) {
        return apiRepository.save(apiEntity);
    }
}
