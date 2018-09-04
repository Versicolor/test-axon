package com.tessi.kyc.event;

import java.util.Date;
import java.util.UUID;

public class FolderCreatedEvent {

    private UUID id;
    private UUID folderTypeId;
    private Date dateCreated;

    public FolderCreatedEvent(UUID id, UUID folderTypeId, Date dateCreated) {
        this.id = id;
        this.folderTypeId = folderTypeId;
        this.dateCreated = dateCreated;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFolderTypeId() {
        return folderTypeId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
