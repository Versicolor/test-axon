package com.tessi.kyc.document.controller;

import java.util.UUID;

public class DocumentPost {

    private UUID folderId;

    private UUID documentTypeId;

    public UUID getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(UUID documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public UUID getFolderId() {
        return folderId;
    }

    public void setFolderId(UUID folderId) {
        this.folderId = folderId;
    }
}
