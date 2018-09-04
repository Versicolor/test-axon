package com.tessi.kyc.document.command;

import java.util.UUID;

public class FolderCreateCommand {

    private final UUID id;
    private final UUID folderTypeId;

    public FolderCreateCommand(UUID id, UUID folderTypeId) {
        this.id = id;
        this.folderTypeId = folderTypeId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFolderTypeId() {
        return folderTypeId;
    }
}
