package org.izumi.jstore.controller;

import io.jmix.core.DataManager;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RolesController {
    private final DataManager dataManager;

    @GetMapping
    public ResponseEntity<Collection<RoleAssignmentEntity>> getRoles(@RequestParam String username) {
        final Collection<RoleAssignmentEntity> roles = dataManager.load(RoleAssignmentEntity.class)
                .query("SELECT e FROM sec_RoleAssignmentEntity e WHERE e.username = :username")
                .parameter("username", username)
                .list();

        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }
}
