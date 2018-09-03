package com.tessi.kyc.document.controller;

import java.util.UUID;

public class DocumentPost {

    private UUID documentTypeId;

    public UUID getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(UUID documentTypeId) {
        this.documentTypeId = documentTypeId;
    }
}
