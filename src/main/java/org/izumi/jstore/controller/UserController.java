package org.izumi.jstore.controller;

import java.util.UUID;

import io.jmix.core.DataManager;
import io.jmix.core.EntitySet;
import io.jmix.rest.security.role.RestMinimalRole;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import lombok.RequiredArgsConstructor;
import org.izumi.jstore.dto.UserDto;
import org.izumi.jstore.entity.User;
import org.izumi.jstore.security.AnonymousRestAccessRole;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
@RequiredArgsConstructor
@RequestMapping("/rest/entities/u")
@RestController
public class UserController extends AbstractController {
    private final DataManager dataManager;

    @PostMapping("/login")
    public ResponseEntity<User> authorize(@RequestBody UserDto user) {
        return ofWithoutHeaders(fit(user.getUsername(), user.getPassword()));
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setId(UUID.randomUUID());
        if (!user.getPassword().contains("{")) {
            user.setPassword("{noop}" + user.getPassword());
        }
        final EntitySet saved = dataManager.save(
                user, assignmentOf(user, AnonymousRestAccessRole.CODE), assignmentOf(user, RestMinimalRole.CODE)
        );
        return ofWithoutHeaders(saved.get(user));
    }

    private RoleAssignmentEntity assignmentOf(User user, String roleCode) {
        return assignmentOf(user.getUsername(), roleCode);
    }

    private RoleAssignmentEntity assignmentOf(String username, String roleCode) {
        final RoleAssignmentEntity entity = dataManager.create(RoleAssignmentEntity.class);
        entity.setUsername(username);
        entity.setRoleCode(roleCode);
        entity.setRoleType("resource");

        return entity;
    }

    private User fit(String username, String password) {
        final User user = dataManager.load(User.class)
                .query("SELECT u FROM User u WHERE u.username = :username")
                .parameter("username", username)
                .one();
        if (!password.contains("{") && user.getPassword().equals("{noop}" + password)) {
            return user;
        }

        throw new IllegalArgumentException("Unknown encoder");
    }
}
