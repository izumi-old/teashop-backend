package org.izumi.jstore.controller;

import java.util.Collection;

import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
@RequiredArgsConstructor
@RequestMapping("/collections")
@RestController
public class CollectionController extends AbstractController {
    private final DataManager dataManager;

    @GetMapping("/tea")
    public ResponseEntity<Collection<org.izumi.jstore.entity.Collection>> getForTea() {
        return of(dataManager.unconstrained().load(org.izumi.jstore.entity.Collection.class)
                .query("SELECT c FROM Collection c WHERE c.type = :type")
                .parameter("type", org.izumi.jstore.entity.Collection.TEA_TYPE)
                .sort(Sort.by("name"))
                .list());
    }

    @GetMapping("/extra")
    public ResponseEntity<Collection<org.izumi.jstore.entity.Collection>> getForExtra() {
        return of(dataManager.unconstrained().load(org.izumi.jstore.entity.Collection.class)
                .query("SELECT c FROM Collection c WHERE c.type = :type")
                .parameter("type", org.izumi.jstore.entity.Collection.EXTRA_TYPE)
                .sort(Sort.by("name"))
                .list());
    }
}
