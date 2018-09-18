package com.tessi.kyc.event;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class FolderCreatedEvent {

    private UUID folderId;
    private UUID folderTypeId;
    private Date dateCreated;

}
