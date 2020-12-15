package com.hti.pos.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ravuthz
 * Date : 12/4/2020, 10:32 PM
 * Email : ravuthz@gmail.com,
 * yovannaravuth@gmail.com
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "settings")
public class Setting extends BaseSetting {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "setting", orphanRemoval = true)
    private List<SettingItem> items = new ArrayList<>();

    public Setting(String name, String description) {
        this.init(name, null, description);
    }

    public Setting(String name, String thumbnail, String description) {
        this.init(name, thumbnail, description);
    }

    @Override
    public String toString() {
        return "Setting { " +
                " name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                " } ";
    }
}
