package com.hti.pos.repository;

import com.hti.pos.domain.Setting;
import com.hti.pos.domain.SettingItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SettingRepositoryTest {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingItemRepository settingItemRepository;

    private Setting createSetting(String name) {
        Setting setting = new Setting(name, name, name);
        setting.setSlug(name);
        return setting;
    }

    private Setting updateSetting(String name, Setting setting) {
        setting.setName(name);
        setting.setSlug(name);
        setting.setThumbnail(name);
        setting.setDescription(name);
        return setting;
    }

    private SettingItem createSettingItem(String name, Setting setting) {
        SettingItem settingItem = new SettingItem(name, name, name, setting);
        settingItem.setValue(name);
        return settingItem;
    }

    private SettingItem updateSettingItem(String name, SettingItem settingItem) {
        settingItem.setName(name);
        settingItem.setSlug(name);
        settingItem.setValue(name);
        settingItem.setThumbnail(name);
        settingItem.setDescription(name);
        return settingItem;
    }

    private final String[] ignoreFields = {"id", "version", "createdAt", "createdBy", "updatedAt", "updatedBy", "description"};
    private final String[] SETTINGS = {"", "test-setting-1", "test-setting-2", "test-setting-3", "test-setting-4"};
    private final String[] SETTING_ITEMS = {
            "",
            "test-setting-item-1",
            "test-setting-item-2",
            "test-setting-item-3",
            "test-setting-item-4",
            "test-setting-item-5",
            "test-setting-item-6",
            "test-setting-item-7"
    };

    @Test
    @DisplayName("Can read, list, create, update, delete settings by SettingRepository")
    public void canCRUDSetting() {
        // Create 2 settings
        settingRepository.deleteAll();
        settingRepository.saveAll(Arrays.asList(
                createSetting(SETTINGS[1]),
                createSetting(SETTINGS[2])
        ));

        // Can read single setting
        Setting setting = settingRepository.findOneByName(SETTINGS[1]);
        assertThat(setting)
                .isNotNull()
                .usingRecursiveComparison().ignoringFields(ignoreFields).isEqualTo(createSetting(SETTINGS[1]));

        // Can list multiple settings
        List<Setting> settingList = (List<Setting>) settingRepository.findAll();
        assertThat(settingList)
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(2)
                .extracting("name").containsOnly(SETTINGS[1], SETTINGS[2]);

        // Can create setting and return setting as result
        Setting setting3 = settingRepository.save(createSetting(SETTINGS[3]));
        assertThat(setting3)
                .isNotNull()
                .usingComparatorForFields((a, b) -> 0, "version")
                .usingRecursiveComparison().ignoringFields(ignoreFields).isEqualTo(createSetting(SETTINGS[3]));

        // Can update setting and return setting as result
        Setting found = settingRepository.findOneByName(SETTINGS[3]);
        Setting setting4 = settingRepository.save(updateSetting(SETTINGS[4], found));
        assertThat(setting4)
                .isNotNull()
                .usingComparatorForFields((a, b) -> 1, "version")
                .usingRecursiveComparison().ignoringFields(ignoreFields).isEqualTo(createSetting(SETTINGS[4]));
    }

    @Test
    @DisplayName("Can read, list, create, update, delete setting's items by SettingItemRepository")
    public void canCRUDSettingItems() {
        // Create 7 setting's items for different settings
        settingRepository.deleteAll();
        settingItemRepository.deleteAll();

        Setting setting3 = settingRepository.save(createSetting(SETTINGS[3]));
        Setting setting4 = settingRepository.save(createSetting(SETTINGS[4]));

        List<SettingItem> settingItems = Arrays.asList(
                createSettingItem(SETTING_ITEMS[1], setting3),
                createSettingItem(SETTING_ITEMS[2], setting3),
                createSettingItem(SETTING_ITEMS[3], setting3),
                createSettingItem(SETTING_ITEMS[4], setting4),
                createSettingItem(SETTING_ITEMS[5], setting4),
                createSettingItem(SETTING_ITEMS[6], setting4)
        );

        settingItemRepository.saveAll(settingItems);

        // Can read single setting's item
        assertThat(settingItemRepository.findOneByName(SETTING_ITEMS[1]))
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields(ignoreFields)
                .isEqualTo(createSettingItem(SETTING_ITEMS[1], setting3));

        // Can list multiple setting's items
        assertThat(settingItemRepository.findAll())
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(settingItems.size())
                .extracting("name")
                .containsOnly(
                        settingItems.stream().map(item -> item.getName()).toArray()
                );

        // Can create setting's item and return setting as result
        assertThat(settingItemRepository.save(createSettingItem(SETTING_ITEMS[7], setting4)))
                .isNotNull()
                .usingComparatorForFields((a, b) -> 0, "version")
                .usingRecursiveComparison().ignoringFields(ignoreFields)
                .isEqualTo(createSettingItem(SETTING_ITEMS[7], setting4));

        // Can update setting's item and return setting as result
        SettingItem found = settingItemRepository.findOneByName(SETTING_ITEMS[7]);
        assertThat(settingItemRepository.save(updateSettingItem(SETTING_ITEMS[7], found)))
                .isNotNull()
                .usingComparatorForFields((a, b) -> 1, "version")
                .usingRecursiveComparison()
                .ignoringFields(ignoreFields)
                .isEqualTo(createSettingItem(SETTING_ITEMS[7], setting4));
    }

    @Test
    @Sql("classpath:test-setting.sql")
    @DisplayName("Can insert data from test-setting.sql")
    public void canInsertSettingBySQL() {
        List<Setting> settingList = (List<Setting>) settingRepository.findAll();
        assertThat(settingList)
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(3)
                .extracting("name").containsOnly(
                "test-setting-3",
                "test-setting-4",
                "script-setting-1",
                "script-setting-2",
                "script-setting-3"
        );
    }
}