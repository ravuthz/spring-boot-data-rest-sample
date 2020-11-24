package com.hti.pos;

import com.hti.pos.domain.Permission;
import com.hti.pos.domain.Role;
import com.hti.pos.domain.User;
import com.hti.pos.repository.PermissionRepository;
import com.hti.pos.repository.RoleRepository;
import com.hti.pos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 2:32 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

//@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRolePermissionTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    @DisplayName("Test Spring Database Instance")
    public void testFindByName() {
        testEntityManager.persist(User.staticUser("Test1", "User"));

        System.out.println("\nUserRepository.findAll()");
        userRepository.findAll().forEach(u -> System.out.println(u));

        List<User> users = userRepository.findAllByFirstNameStartingWithIgnoreCase("test");
        assertEquals(1, users.size());

        assertThat(users).extracting(User::getFirstName).containsOnly("Test1");
    }

    @Test
    @DisplayName("Test Create User with Roles and Permissions")
    public void createUserWithRolePermissions() {
        var permissions = Arrays.asList(
                new Permission("SHOW_UAT", "UAT can show application"),
                new Permission("LIST_UAT", "UAT can list application"),
                new Permission("CREATE_UAT", "UAT can create application"),
                new Permission("UPDATE_UAT", "UAT can update application"),
                new Permission("DELETE_UAT", "UAT can delete application")
        );
        permissionRepository.saveAll(permissions);

        var role = new Role("UAT");
        roleRepository.save(role);

        var auditPermission = new Permission("AUDIT_UAT", "UAT can audit application");
        auditPermission.getRoles().add(role);
        permissionRepository.save(auditPermission);

        role.getPermissions().addAll(permissions);
        roleRepository.save(role);

        var user = User.staticUser("uat", "user");
        user.getRoles().add(role);
        userRepository.save(user);

        System.out.println("\nPermissionRepository.findAll()");
        permissionRepository.findAll().forEach(p -> System.out.println(p));

        System.out.println("\nRoleRepository.findAll()");
        roleRepository.findAll().forEach(r -> System.out.println(r));

        System.out.println("\nUserRepository.findAll()");
        userRepository.findAll().forEach(u -> System.out.println(u));

        long startTime1 = System.nanoTime();
        System.out.println("\nUserRepository.findAllByLastNameContainsIgnoreCase()");
        userRepository.findAllByLastNameContainsIgnoreCase("account").forEach(u -> System.out.println(u));
        System.out.println("Duration 1: " + (System.nanoTime() - startTime1));

        long startTime2 = System.nanoTime();
        System.out.println("\nUserRepository.findByLastNameEndsWith()");
        userRepository.findByLastNameEndsWith("account").forEach(u -> System.out.println(u));
        System.out.println("Duration 2: " + (System.nanoTime() - startTime2));

        long startTime3 = System.nanoTime();
        System.out.println("\nUserRepository.findByLastNameLikeIgnoreCase()");
        userRepository.findByLastNameLikeIgnoreCase("account").forEach(u -> System.out.println(u));
        System.out.println("Duration 3: " + (System.nanoTime() - startTime3));

    }

}
