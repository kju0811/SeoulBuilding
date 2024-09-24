package Project.SeoulBuilding.backend.Controller;


import Project.SeoulBuilding.backend.repository.ApiRepository;
import Project.SeoulBuilding.backend.sevice.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

    @Value("${servicekey}")
    private String servicekey;

    @Value("{openapiUrl}")
    private String openapiUrl;

    @PostMapping("/building")
    public String Building (@RequestParam("name") String name, Model model ) {
        String result = "";
//454b526c4c6d656d313331544a57526b api 인증키


/*
        try {
            String requestdata = name;
            URL url = new URL("http://openapi.seoul.go.kr:8088/454b526c4c6d656d313331544a57526b/json/tbEntranceItem/1/1000/"
                    + requestdata);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONObject tbEntranceItem = (JSONObject)jsonObject.get("tbEntranceItem");
            Long totalCount =(Long)tbEntranceItem.get("list_total_count");

            JSONObject subresult =(JSONObject)tbEntranceItem.get("result");
            JSONArray infoArr = (JSONArray)tbEntranceItem.get("row");

            for(int i=0;i<infoArr.size()  ;i++) {
                JSONObject tmp = (JSONObject)infoArr.get(i);
                ApiEntity infoObj = new ApiEntity(
                        i+(long)1,
                        (long)tmp.get("NEW_ADDR_ID"),
                        (long)tmp.get("FCLT_ID"),
                        (String)tmp.get("FCLT_NM"),
                        (double)tmp.get("LAT"),
                        (double)tmp.get("LOT"),
                        (String)tmp.get("BLDN_CLNY_YN"),
                        (String)tmp.get("FCLT_USG_SE"),
                        (String)tmp.get("RDN_ADDR"),
                        (String)tmp.get("LOTNO_ADDR"),
                        (String)tmp.get("ETC"),
                        (String)tmp.get("NTN_BRNCH_NO"),
                        (LocalDateTime)tmp.get("DATA_CRTR_DD"));

            }3607개
        } catch (Exception e){
            log.trace("An error occurred while processing: ", e);
        }
        return "redirect:/search";*/
        return result;
    }
}
