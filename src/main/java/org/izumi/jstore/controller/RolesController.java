package org.izumi.jstore.controller;

import io.jmix.core.DataManager;
import io.jmix.core.EntitySerialization;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RolesController extends AbstractController {
    private final DataManager dataManager;
    private final EntitySerialization entitySerialization;

    @GetMapping(params = {"username"})
    public ResponseEntity<String> getRoles(@RequestParam String username) {
        final Collection<RoleAssignmentEntity> roles = dataManager.load(RoleAssignmentEntity.class)
                .query("SELECT e FROM sec_RoleAssignmentEntity e WHERE e.username = :username")
                .parameter("username", username)
                .list();

        return ofWithoutHeaders(entitySerialization.toJson(roles));
    }
}
