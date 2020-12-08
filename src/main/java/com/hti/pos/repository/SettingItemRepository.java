package com.hti.pos.repository;

import com.hti.pos.domain.SettingItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "setting-items", collectionResourceRel = "setting-items")
public interface SettingItemRepository extends PagingAndSortingRepository<SettingItem, Long>,
        JpaSpecificationExecutor<SettingItem> {
    SettingItem findOneByName(String name);
}
