package Project.SeoulBuilding.backend.Api.controller;

import Project.SeoulBuilding.backend.Api.entity.ApiEntity;

import Project.SeoulBuilding.backend.Api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class ApiController {
    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/building")
    public ApiEntity saveApi(ApiEntity apiEntity) {
        return apiService.Building(apiEntity);
    }

    @GetMapping("/findbuilding")
    public List<ApiEntity> getBuilding() {
        return apiService.getBuilding();
    }

    @GetMapping("/search")
    public List<ApiEntity> findbuilding(@RequestParam String building) {
        return apiService.findBuilding(building);
    }
}
