package com.ssafy.andback.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
*
* BaseEntity
* 만든 시간과 수정 시간이 있는 BaseEntity
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-19
* 마지막 수정일 2022-04-19
**/

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {
    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        modifiedDate = now;
    }

    @PreUpdate
    public void preUpdate(){
        modifiedDate = LocalDateTime.now();
    }
}
