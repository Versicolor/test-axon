package com.tessi.kyc.event;

import java.util.Date;
import java.util.UUID;

public class ControlCreatedEvent {

    private final UUID id;
    private final UUID documentId;
    private final UUID controlTypeId;
    private final Date dateCreate;

    public ControlCreatedEvent(UUID id, UUID documentId, UUID controlTypeId, Date dateCreate) {
        this.id = id;
        this.documentId = documentId;
        this.controlTypeId = controlTypeId;
        this.dateCreate = dateCreate;
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

    public Date getDateCreate() {
        return dateCreate;
    }
}
