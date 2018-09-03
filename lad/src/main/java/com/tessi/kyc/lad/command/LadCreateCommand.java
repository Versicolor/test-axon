package com.tessi.kyc.lad.command;

import java.util.UUID;

public class LadCreateCommand {

    private final UUID id;
    private final UUID documentId;

    public LadCreateCommand(UUID id, UUID documentId) {
        this.id = id;
        this.documentId = documentId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentId() {
        return documentId;
    }
}
