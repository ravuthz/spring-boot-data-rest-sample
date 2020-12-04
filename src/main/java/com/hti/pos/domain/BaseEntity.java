package com.hti.pos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
public class BaseEntity {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    private Date dateCreated;

    @JsonIgnore
    private Date lastUpdated;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = new Date();
        if (dateCreated == null) {
            dateCreated = new Date();
        }
    }
}

