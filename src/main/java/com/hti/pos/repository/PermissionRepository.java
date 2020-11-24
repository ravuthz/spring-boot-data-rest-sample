package com.hti.pos.repository;

import com.hti.pos.domain.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 3:56 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Repository
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
    Permission findByName(String permission);
    List<Permission> findAllByNameIgnoreCaseStartingWith(String text);
}
