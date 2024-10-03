package Project.SeoulBuilding.backend.Api.service;

import Project.SeoulBuilding.backend.Api.entity.ApiEntity;
import Project.SeoulBuilding.backend.Api.repository.ApiRepository;
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
