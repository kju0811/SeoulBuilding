package Project.SeoulBuilding.backend.Api.service;

import Project.SeoulBuilding.backend.Api.entity.ApiEntity;
import Project.SeoulBuilding.backend.Api.repository.ApiRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class ApiService {
    private final ApiRepository apiRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Value("${serviceKey}")
    private String serviceKey;

    @Value("${openapiUrl}")
    private String openapiUrl;

    public ApiEntity Building(ApiEntity apiEntity) {
        log.info("서비스 시작");
        int totalProcessed = 0;

        try {
            for (int pg = 1; pg < 2; pg++) {
                try {
                    int start = 1;
                    int end = 300;
                    String apiUrl = openapiUrl + serviceKey + "/json/" + "tbEntranceItem/" + start + "/" + end;
                    log.info("페이지 {} API 호출: {}", pg, apiUrl);

                    String result = restTemplate.getForObject(apiUrl, String.class);
                    if (result == null || result.isEmpty()) {
                        log.error("페이지 {} API 응답이 비어있습니다", pg);
                        continue; // 다음 페이지로 넘어감
                    }

                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                    JSONObject tbEntranceItem = (JSONObject) jsonObject.get("tbEntranceItem");
                    if (tbEntranceItem == null) {
                        log.error("페이지 {} tbEntranceItem이 null입니다", pg);
                        continue;
                    }

                    JSONArray row = (JSONArray) tbEntranceItem.get("row");
                    if (row == null) {
                        log.error("페이지 {} row가 null입니다", pg);
                        continue;
                    }

                    for (int i = 0; i < row.size(); i++) {
                        try {
                            JSONObject data = (JSONObject) row.get(i);
                            ApiEntity apidata = new ApiEntity(
                                    (String) data.get("ID"),
                                    (String) data.get("NEW_ADDR_ID"),
                                    (String) data.get("FCLT_ID"),
                                    (String) data.get("FCLT_NM"),
                                    (double) data.get("LAT"),
                                    (double) data.get("LOT"),
                                    (String) data.get("BLDN_CLNY_YN"),
                                    (String) data.get("FCLT_USG_SE"),
                                    (String) data.get("RDN_ADDR"),
                                    (String) data.get("LOTNO_ADDR"),
                                    (String) data.get("ETC"),
                                    (String) data.get("NTN_BRNCH_NO")
                            );
                            apiRepository.save(apidata);// 리스트에 데이터 추가
                        } catch (Exception e) {
                            log.error("데이터 처리 중 오류 발생 - 페이지: {}, 인덱스: {}, 오류: {}", pg, i, e.getMessage());
                        }
                    }

                    log.info("페이지 {} 처리 완료", pg);

                } catch (Exception e) {
                    log.error("페이지 {} 처리 중 오류 발생: {}", pg, e.getMessage());
                    break; // 오류 발생 시 반복문 종료
                }
            }

        } catch (Exception e) {
            log.error("전체 처리 중 오류 발생: ", e);
        }

        log.info("서비스 종료 - 총 {}개 데이터 처리됨", totalProcessed);
        return apiEntity;
    }

    public List<ApiEntity> getBuilding() {
        return apiRepository.findAll();
    }

    public List<ApiEntity> findBuilding(String building) {
        return apiRepository.findBuilding(building);
    }

    @Configuration
    public static class RestTemplateConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
}