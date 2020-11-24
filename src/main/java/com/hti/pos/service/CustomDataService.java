package com.hti.pos.service;

import com.hti.pos.domain.Permission;
import com.hti.pos.domain.Role;
import com.hti.pos.domain.User;
import com.hti.pos.repository.PermissionRepository;
import com.hti.pos.repository.RoleRepository;
import com.hti.pos.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public CustomDataService(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public void assignPermissionsToRole(List<Permission> permissions, String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role != null) {
            permissions.forEach(role.getPermissions()::add);
            roleRepository.save(role);
        }
    }

    private void assignRoleToUser(Role role, String userName) {
        User user = userRepository.findByUsername(userName);
        if (user != null) {
            user.getRoles().add(role);
            userRepository.save(user);
            role.getUsers().add(user);
            roleRepository.save(role);
        }
    }

    public List<Permission> generatePermissions(String code, String name) {
        code = code.toUpperCase();
        List<Permission> permissions = Arrays.asList(
                new Permission("SHOW_" + code, "Can show " + name),
                new Permission("LIST_" + code, "Can list " + name),
                new Permission("CREATE_" + code, "Can create " + name),
                new Permission("UPDATE_" + code, "Can update " + name),
                new Permission("DELETE_" + code, "Can delete " + name)
        );
        permissionRepository.saveAll(permissions);
        return permissions;
    }

    public Role generateRole(String name) {
        return roleRepository.save(new Role(name, "Role as " + name));
    }

    public void generateData() {
        log.debug("Start generate permissions");
        List<Permission> appPermissions = generatePermissions("app", "Application");
        List<Permission> userPermissions = generatePermissions("user", "User");
        List<Permission> rolePermissions = generatePermissions("role", "Role");
        List<Permission> permPermissions = generatePermissions("perm", "Permission");

        log.debug("Start generate roles");
        Role userRole = generateRole("user");
        Role adminRole = generateRole("admin");
        Role editorRole = generateRole("editor");

        log.debug("Start generate users");
        userRepository.saveAll(Arrays.asList(
                User.staticUser("user", "account"),
                User.staticUser("admin", "account"),
                User.staticUser("editor", "account")
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
