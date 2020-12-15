package com.hti.pos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
//@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    private long version;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @PreUpdate
    @PrePersist
    public void updateAuditFields() {
        updatedAt = new Date();
        updatedBy = getUsernameOfAuthenticatedUser("system");
        if (createdAt == null) {
            createdAt = new Date();
            createdBy = getUsernameOfAuthenticatedUser("system");
        }
    }

    public String getUsernameOfAuthenticatedUser(String defaultUsername) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return defaultUsername;
        }
        return ((User) authentication.getPrincipal()).getUsername();
    }
}

