package com.tessi.kyc.event;

import java.util.Date;
import java.util.UUID;

public class LadCreatedEvent {

    private final UUID id;
    private final UUID documentId;
    private final Date dateCreate;

    public LadCreatedEvent(UUID id, UUID documentId, Date dateCreate) {
        this.id = id;
        this.documentId = documentId;
        this.dateCreate = dateCreate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public Date getDateCreate() {
        return dateCreate;
    }
}
