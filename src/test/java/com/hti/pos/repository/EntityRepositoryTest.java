package com.hti.pos.repository;

import com.hti.pos.domain.Setting;
import com.hti.pos.domain.SettingItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
public class EntityRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    private EntityRepository settingRepo;
    private EntityRepository settingItemRepo;

    @BeforeEach
    void setUp() {
        this.settingRepo = new EntityRepository(Setting.class, this.entityManager);
        this.settingItemRepo = new EntityRepository(SettingItem.class, this.entityManager);

        Setting setting = new Setting("test setting", "test-setting-thumbnail", "test setting");
        settingRepo.save(setting);
        assertThat(setting.getId()).isNotNull();

        SettingItem item1 = new SettingItem("test-set-item-1", "test-set-item-1", "test-set-item-1", setting);
        SettingItem item2 = new SettingItem("test-set-item-2", "test-set-item-2", "test-set-item-2", setting);
        SettingItem item3 = new SettingItem("test-set-item-3", "test-set-item-3", "test-set-item-3", setting);

        List<SettingItem> items = Arrays.asList(item1, item2, item3);
        settingItemRepo.saveAll(items);

        settingItemRepo.findAll().forEach(item -> {
            assertThat(((SettingItem) item).getId() != null);
        });
    }

    @Test
    public void testUpdate() {
        settingRepo.findById(1L).ifPresent(item -> {
            ((Setting) item).setDescription("Update setting description");
            assertThat(item).isNotNull();
        });
    }


    @Test
    public void testRead() {
        List all = settingRepo.findAll();
        System.out.println(all);
        System.out.println(all.size());
        assertThat(all).isNotEmpty();

//        settingRepo.findById(1L).ifPresent(item -> {
//            assertThat(item).isNotNull();
//        });
//
//        settingRepo.deleteAll();
    }

}