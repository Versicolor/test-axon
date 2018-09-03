package com.tessi.kyc.control.command;

import java.util.UUID;

public class ControlCreateCommand {

    private final UUID id;
    private final UUID documentId;
    private final UUID controlTypeId;

    public ControlCreateCommand(UUID id, UUID documentId, UUID controlTypeId) {
        this.id = id;
        this.documentId = documentId;
        this.controlTypeId = controlTypeId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public UUID getControlTypeId() {
        return controlTypeId;
    }
}
