package com.tessi.kyc.event;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class DocumentCreatedEvent {

    private UUID documentId;
    private UUID documentTypeId;
    private UUID folderId;
    private Date dateCreated;
    private String name;
    private String status;

}
