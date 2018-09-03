package com.tessi.kyc.event;

import java.util.Date;
import java.util.UUID;

public class DocumentCreatedEvent {

    private UUID id;
    private UUID documentTypeId;
    private Date dateCreated;

    public DocumentCreatedEvent(UUID id, UUID documentTypeId, Date dateCreated) {
        this.id = id;
        this.documentTypeId = documentTypeId;
        this.dateCreated = dateCreated;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentTypeId() {
        return documentTypeId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
