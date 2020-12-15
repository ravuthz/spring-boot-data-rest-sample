package com.hti.pos.service.data;

import com.hti.pos.domain.Setting;
import com.hti.pos.domain.SettingItem;
import com.hti.pos.repository.SettingItemRepository;
import com.hti.pos.repository.SettingRepository;
import com.hti.pos.specification.SearchCriteria;
import com.hti.pos.specification.SearchOperation;
import com.hti.pos.specification.SearchSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SettingSettingItemDataService {
    private final SettingRepository settingRepository;
    private final SettingItemRepository settingItemRepository;

    @Autowired
    public SettingSettingItemDataService(SettingRepository settingRepository, SettingItemRepository settingItemRepository) {
        this.settingRepository = settingRepository;
        this.settingItemRepository = settingItemRepository;
    }

    public Setting newOrUpdate(String name, String thumbnail, String description) {
        Setting setting = settingRepository.findOneByName(name);
        if (setting == null) {
            setting = new Setting(name, thumbnail, description);
            settingRepository.save(setting);
        }
        return setting;
    }

    public SettingItem newOrUpdateItem(String name, String value, String description, Setting setting) {
        SettingItem item = settingItemRepository.findOneByName(name);
        if (item == null) {
            item = new SettingItem(name, value, description);
            item.setSetting(setting);
            settingItemRepository.save(item);
        }
        return item;
    }

    public SettingItem newOrUpdateItem(String name, String value, String description) {
        return newOrUpdateItem(name, value, description, null);
    }

    public void generateData() {
        log.debug("SettingSettingItemDataService.generateData()");

        Setting setting1 = newOrUpdate("Site", "website.png", "All settings for website");

        SettingItem item1 = newOrUpdateItem("Title", "POS System", "Set title for website", setting1);
        SettingItem item2 = newOrUpdateItem("Email", "admin@admin.com", "Set email for website", setting1);
        SettingItem item3 = newOrUpdateItem("Phone", "0964577770", "Set phone number for website", setting1);

        List<SettingItem> items = Arrays.asList(item1, item2, item3);
//        settingItemRepository.saveAll(items);

//        setting1.getItems().addAll(items);
//        settingRepository.save(setting1);


        SearchSpecification<SettingItem> searchSpecification = new SearchSpecification<>();
        searchSpecification.add(new SearchCriteria("name", "title", SearchOperation.MATCH));
        searchSpecification.add(new SearchCriteria("value", "pos", SearchOperation.MATCH));

        settingItemRepository.findAll(searchSpecification)
                .forEach(item -> System.out.println("Found item: " + item));

        settingRepository.findAll((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.join("items", JoinType.INNER).get("name"), "item-21");
        }).forEach(item -> {
            System.out.println("Search found item: " + item);
        });
    }
}
