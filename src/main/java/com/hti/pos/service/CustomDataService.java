package com.hti.pos.service;

import com.hti.pos.domain.Permission;
import com.hti.pos.domain.Role;
import com.hti.pos.domain.User;
import com.hti.pos.repository.PermissionRepository;
import com.hti.pos.repository.RoleRepository;
import com.hti.pos.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ravuthz
 * Date : 11/23/2020, 4:25 PM
 * Email : ravuthz@gmail.com, yovannaravuth@gmail.com
 */

@Slf4j
@Service
public class CustomDataService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public CustomDataService(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public void assignPermissionsToRole(List<Permission> permissions, String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role != null) {
//            role.getPermissions().addAll(permissions);
            role.getPermissions().forEach(role.getPermissions()::add);
            roleRepository.save(role);
        }
    }

    private void assignRoleToUser(Role role, String userName) {
        User user = userRepository.findByUsername(userName);
        if (user != null) {
            user.getRoles().add(role);
            userRepository.save(user);
//            role.getUsers().add(user);
//            roleRepository.save(role);
        }
    }

    public Set<Permission> generatePermissions(String code, String name) {
        code = code.toUpperCase();
        List<Permission> permissions = permissionRepository.findAllByNameIgnoreCaseEndingWith(code);
        if (permissions.isEmpty()) {
            permissions = Arrays.asList(
                    new Permission("SHOW_" + code, "Can show " + name),
                    new Permission("LIST_" + code, "Can list " + name),
                    new Permission("CREATE_" + code, "Can create " + name),
                    new Permission("UPDATE_" + code, "Can update " + name),
                    new Permission("DELETE_" + code, "Can delete " + name)
            );
            permissionRepository.saveAll(permissions);
        }
        return new HashSet<Permission>(permissions);
    }

    public Role generateRole(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name, "Role as " + name);
            role = roleRepository.save(role);
        }
        return role;
    }

    public User generateUser(String firstName, String lastName) {
        User user = userRepository.findByUsername(firstName);
        if (user == null) {
            user = User.staticUser(firstName, lastName);
            userRepository.save(user);
        }
        return user;
    }

    public void generateData() {
        log.debug("Start generate permissions");
        Set<Permission> appPermissions = generatePermissions("app", "Application");
        Set<Permission> userPermissions = generatePermissions("user", "User");
        Set<Permission> rolePermissions = generatePermissions("role", "Role");
        Set<Permission> permPermissions = generatePermissions("perm", "Permission");

        log.debug("Start generate roles");
        Role userRole = generateRole("user");
        Role adminRole = generateRole("admin");
        Role editorRole = generateRole("editor");

        log.debug("Start generate users");
        userRepository.saveAll(Arrays.asList(
                generateUser("user", "account"),
                generateUser("admin", "account"),
                generateUser("editor", "account")
        ));

        List<Permission> showOnlyPermissions = permissionRepository.findAllByNameIgnoreCaseStartingWith("SHOW_");
        List<Permission> listOnlyPermissions = permissionRepository.findAllByNameIgnoreCaseStartingWith("LIST_");
        List<Permission> permissionsForUser = new ArrayList<>();
        permissionsForUser.addAll(showOnlyPermissions);
        permissionsForUser.addAll(listOnlyPermissions);

        List<Permission> updateOnlyPermission = permissionRepository.findAllByNameIgnoreCaseStartingWith("UPDATE_");
        List<Permission> permissionsForEditor = new ArrayList<>(updateOnlyPermission);
        permissionsForEditor.addAll(showOnlyPermissions);
        permissionsForEditor.addAll(listOnlyPermissions);

        List<Permission> permissionsForAdmin = new ArrayList<>(appPermissions);
        permissionsForAdmin.addAll(userPermissions);
        permissionsForAdmin.addAll(rolePermissions);
        permissionsForAdmin.addAll(permPermissions);

        log.debug("Start assign permissions to role ...");
        assignPermissionsToRole(permissionsForUser, "user");
        assignPermissionsToRole(permissionsForAdmin, "admin");
        assignPermissionsToRole(permissionsForEditor, "editor");

        log.debug("Start assign role to user ...");
        assignRoleToUser(userRole, "user");
        assignRoleToUser(adminRole, "admin");
        assignRoleToUser(editorRole, "editor");
    }
}
