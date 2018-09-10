package com.tessi.kyc.document.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class UpdateDocStatusCommand {

    @TargetAggregateIdentifier
    private UUID folderId;
    private UUID documentId;
    private String status;

    public UpdateDocStatusCommand(UUID folderId, UUID documentId, String status) {
        this.folderId = folderId;
        this.documentId = documentId;
        this.status = status;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public String getStatus() {
        return status;
    }

    public UUID getFolderId() {
        return folderId;
    }

}
