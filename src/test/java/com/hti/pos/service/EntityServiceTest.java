package com.hti.pos.service;

import com.hti.pos.domain.Setting;
import com.hti.pos.domain.SettingItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class EntityServiceTest {

    @Autowired
    private EntityService service;

    @Test
    public void findAll() {
        List<Setting> list = service.findAll(Setting.class);
        log.debug("list: " + list);
        assertThat(list).isNotEmpty();

        List<SettingItem> listItems = service.findAll(SettingItem.class);
        log.debug("listItems: " + listItems);
        assertThat(listItems).isNotEmpty();
    }

    @Test
    public void save() {
        Setting setting = new Setting("test1", "test1.png", "Test 1");

        Setting entity = (Setting) service.save(Setting.class, setting);
        System.out.println("entity: " + entity);
        assertThat(entity).isNotNull();
    }

    @Test
    public void count() {
        long count = service.count(Setting.class);
        System.out.println("Count: " + count);
        assertThat(count).isGreaterThan(0);
    }

    @Test
    public void delete() {
        Optional setting = service.findById(Setting.class, 2L);
        assertThat(setting).isNotNull();
        assertThat(setting.isPresent()).isTrue();
        assertThat(setting.isEmpty()).isFalse();

        service.delete(Setting.class, setting);
        assertThat(setting).isNull();
    }
}