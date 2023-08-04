package com.surfscribebackend.surfscribe.backend.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Object entityId) {
        super("Entity " + entityName + " with id " + entityId + " not found");
    }
}
