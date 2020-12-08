package com.hti.pos.repository;

import com.hti.pos.domain.Setting;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "settings", collectionResourceRel = "settings")
public interface SettingRepository extends PagingAndSortingRepository<Setting, Long>,
        JpaSpecificationExecutor<Setting> {
    Setting findOneByName(String name);
}


