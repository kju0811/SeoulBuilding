package Project.SeoulBuilding.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table( name = "Apidata")
public class ApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private long ID;//건물 아이디

    private long NEW_ADDR_ID;//새주소 아이디
    private long FCLT_ID;//시설아이디
    private String FCLT_NM;//시설명
    private double LAT;//위도
    private double LOT;//경도
    private String BLDN_CLNY_YN;//건물군 여부
    private String FCLT_USG_SE;//시설용도 분류
    private String RDN_ADDR;//소재지 도로명주소
    private String LOTNO_ADDR;//소재 지번주소
    private String ETC;//기타
    private String NTN_BRNCH_NO;// 국가지점번호
    private LocalDateTime DATA_CRTR_DD;//데이터기준 일자
 }
