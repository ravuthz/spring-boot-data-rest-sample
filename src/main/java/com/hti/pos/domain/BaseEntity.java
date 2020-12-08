package com.hti.pos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 1:25 PM
 * Email : ravuthz@gmail.com,
 * yovannaravuth@gmail.com
 */

@Setter
@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

//    @CreatedDate
    private Date createdAt;

//    @LastModifiedDate
    private Date updatedAt;

//    @CreatedBy
    private String createdBy;

//    @LastModifiedBy
    private String updatedBy;

    @PreUpdate
    @PrePersist
    public void updateAuditFields() {
        updatedAt = new Date();
        if (createdAt == null) {
            createdAt = new Date();
        }

        // TODO: Update Audit User here ...
    }
}

