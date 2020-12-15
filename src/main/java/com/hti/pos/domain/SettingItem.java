package com.hti.pos.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by ravuthz
 * Date : 12/4/2020, 10:57 PM
 * Email : ravuthz@gmail.com,
 * yovannaravuth@gmail.com
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "setting_items")
public class SettingItem extends BaseSetting {

    public SettingItem(String name, String thumbnail, String description, Setting setting) {
        this.init(name, thumbnail, description);
        this.setting = setting;
    }

    public SettingItem(String name, String value, String description) {
        this.init(name, "", description);
        this.value = value;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setting")
    private Setting setting;

    @Column
    private String value;

    @Override
    public String toString() {
        return "SettingItem { " +
                "setting=" + (setting != null ? setting.getName() : null) +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", value='" + value + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                " } ";
    }
}
