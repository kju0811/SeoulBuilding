package Project.SeoulBuilding.backend.Api.service;

import Project.SeoulBuilding.backend.Api.entity.ApiEntity;
import Project.SeoulBuilding.backend.Api.repository.ApiRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
public class ApiService {
    private final ApiRepository apiRepository;

    @Value("${serviceKey}")
    private String serviceKey;

    @Value("${openapiUrl}")
    private String openapiUrl;

    public String Building(ApiEntity apiEntity) {
        String result = "";
        try {
            for (int pg = 1; pg < 36; pg++) {// 3607개의 데이터를 /5로 분산
                URL url = new URL(openapiUrl
                        + serviceKey + "/json/" + "tbEntranceItem/" +pg+ "/100" );

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-type", "application/json");

                BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                result = bf.readLine();

                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
                JSONObject tbEntranceItem = (JSONObject) jsonObject.get("tbEntranceItem");
                JSONArray row = (JSONArray) tbEntranceItem.get("row");

                for (int i = 0; i < row.size(); i++) {
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
                    apiRepository.save(apidata);
                }
            }
        } catch (Exception e) {
            log.trace(e.getMessage());
        }
        return apiEntity.toString();
    }
    @Autowired
    public ApiService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }
}
