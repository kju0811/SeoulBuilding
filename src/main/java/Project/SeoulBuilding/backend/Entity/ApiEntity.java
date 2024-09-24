package Project.SeoulBuilding.backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table( name = "Apidata")
public class ApiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private long ID;

    private long NEW_ADDR_ID;
    private long FCLT_ID;
    private String FCLT_NM;
    private double LAT;
    private double LOT;
    private String BLDN_CLNY_YN;
    private String FCLT_USG_SE;
    private String RDN_ADDR;
    private String LOTNO_ADDR;
    private String ETC;
    private String NTN_BRNCH_NO;
    private LocalDateTime DATA_CRTR_DD;
 }
