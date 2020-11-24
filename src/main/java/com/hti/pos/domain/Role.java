package com.hti.pos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 1:49 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5403723535622562579L;

    @NotEmpty
    private String name;

    private String note;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "rolePermission",
            joinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "permissionId", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<Permission> permissions = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String note) {
        this.name = name;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
