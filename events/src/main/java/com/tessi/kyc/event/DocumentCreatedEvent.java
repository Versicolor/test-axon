package com.tessi.kyc.event;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class DocumentCreatedEvent {

    private UUID id;
    private UUID documentTypeId;
    private Date dateCreated;
    private String name;

}
