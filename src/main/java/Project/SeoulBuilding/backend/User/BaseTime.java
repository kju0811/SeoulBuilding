package Project.SeoulBuilding.backend.User;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/*
유저,권한 엔티티에 뿌려서 사용
 */
@Getter
@MappedSuperclass
public class BaseTime {
    // 생성 시간 관리
    @CreationTimestamp
    @Column(name = "createdAt",nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    //업데이트 시간 관리
    @UpdateTimestamp
    @Column(name = "update_date", updatable = false)
    private LocalDateTime updateDate;
}
