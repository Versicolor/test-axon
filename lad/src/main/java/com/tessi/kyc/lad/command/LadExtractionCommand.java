package com.tessi.kyc.lad.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class LadExtractionCommand {

    @TargetAggregateIdentifier
    private final UUID id;
    private final UUID documentId;

    public LadExtractionCommand(UUID id, UUID documentId) {
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
