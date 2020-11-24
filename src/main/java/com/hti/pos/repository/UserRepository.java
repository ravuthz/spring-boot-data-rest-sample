package com.hti.pos.repository;

import com.hti.pos.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 3:13 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAllByFirstNameOrLastNameContainsIgnoreCase(String firstName, String lastName);

    List<User> findAllByFirstNameStartingWithIgnoreCase(String firstName);

    List<User> findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(String firstName, String lastName);

    List<User> findAllByLastNameContainsIgnoreCase(String lastName);

    @Query("select u from User u where u.lastName like %?1")
    List<User> findByLastNameEndsWith(String lastName);

    @Query(value = "SELECT * FROM users WHERE last_name ILIKE %?1%", nativeQuery = true)
    List<User> findByLastNameLikeIgnoreCase(String lastName);

}
