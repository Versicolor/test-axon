package com.tessi.kyc.document.command;

import java.util.UUID;

public class DocumentCreateCommand {

    private final UUID id;
    private final UUID folderId;
    private final UUID documentTypeId;

    public DocumentCreateCommand(UUID id, UUID folderId, UUID documentTypeId) {
        this.id = id;
        this.folderId = folderId;
        this.documentTypeId = documentTypeId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentTypeId() {
        return documentTypeId;
    }

    public UUID getFolderId() {
        return folderId;
    }
}
