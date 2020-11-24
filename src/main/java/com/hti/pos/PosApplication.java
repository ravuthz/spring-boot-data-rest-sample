package com.hti.pos;

import com.hti.pos.repository.PermissionRepository;
import com.hti.pos.repository.RoleRepository;
import com.hti.pos.repository.UserRepository;
import com.hti.pos.service.CustomDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PosApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public static void main(String[] args) {
        SpringApplication.run(PosApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.debug("StartApplication...");
        CustomDataService customDataService = new CustomDataService(userRepository, roleRepository, permissionRepository);
        customDataService.generateData();
    }

}
