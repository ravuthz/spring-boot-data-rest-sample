package com.hti.pos;

import com.hti.pos.domain.Setting;
import com.hti.pos.domain.SettingItem;
import com.hti.pos.repository.SettingItemRepository;
import com.hti.pos.repository.SettingRepository;
import com.hti.pos.service.data.SettingSettingItemDataService;
import com.hti.pos.service.data.UserRolePermissionDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class PostApplicationRunner implements ApplicationRunner {
    @Autowired
    private UserRolePermissionDataService userRolePermissionDataService;

    @Autowired
    private SettingSettingItemDataService settingSettingItemDataService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("PostApplicationRunner.run()");
        userRolePermissionDataService.generateData();
        settingSettingItemDataService.generateData();
    }
}
