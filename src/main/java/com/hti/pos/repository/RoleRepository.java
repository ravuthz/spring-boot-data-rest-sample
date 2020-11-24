package com.hti.pos.repository;

import com.hti.pos.domain.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 3:54 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findByName(String role);

    List<Role> findAllByNameIsLike(String role);

    List<Role> findAllByNameContains(String role);
}
