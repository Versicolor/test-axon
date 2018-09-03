package com.tessi.kyc.event;

import java.util.UUID;

public class ControlsCreatedEvent {

    private final UUID documentId;

    public ControlsCreatedEvent(UUID documentId) {
        this.documentId = documentId;
    }

    public UUID getDocumentId() {
        return documentId;
    }
}
