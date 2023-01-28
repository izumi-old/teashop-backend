package org.izumi.jstore.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
public abstract class AbstractController {
    protected <T> ResponseEntity<T> of(T object) {
        return new ResponseEntity<>(object, headers(), HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> ofWithoutHeaders(T object) {
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    protected HttpHeaders headers() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        return headers;
    }
}
