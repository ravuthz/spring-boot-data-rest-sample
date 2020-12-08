package com.hti.pos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 1:53 PM
 * Email : ravuthz@gmail.com,
 * yovannaravuth@gmail.com
 */
@Setter
@Getter
@Entity
@Table(name = "permissions")
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7085459662547835139L;

    @NotEmpty
    @Column(unique = true)
    private String name;

    private String note;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    public Permission(String name) {
        this.name = name;
    }

    public Permission(String name, String note) {
        this.name = name;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", roles=" + roles +
                '}';
    }
}

