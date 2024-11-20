package Project.SeoulBuilding.backend.Api.controller;


import Project.SeoulBuilding.backend.Api.entity.ApiEntity;
import Project.SeoulBuilding.backend.Api.repository.ApiRepository;
import Project.SeoulBuilding.backend.Api.service.ApiService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;
    private final ApiRepository apiRepository;

    @Autowired
    public ApiController(ApiService apiService, ApiRepository apiRepository) {
        this.apiService = apiService;
        this.apiRepository = apiRepository;
    }

    @GetMapping("/building")
    public String Building(ApiEntity apiEntity) {
        return apiService.Building(apiEntity);
    }
}
