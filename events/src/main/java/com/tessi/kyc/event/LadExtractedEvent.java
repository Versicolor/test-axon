package com.tessi.kyc.event;

import java.util.Date;
import java.util.UUID;

public class LadExtractedEvent {

    private final UUID id;
    private final UUID documentId;
    private final Date dateExtracted;
    private final String data;

    public LadExtractedEvent(UUID id, UUID documentId, Date dateExtracted, String data) {
        this.id = id;
        this.documentId = documentId;
        this.dateExtracted = dateExtracted;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public Date getDateExtracted() {
        return dateExtracted;
    }

    public String getData() {
        return data;
    }
}
