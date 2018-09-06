package com.tessi.kyc.event;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class DocumentUpdatedEvent {

    private UUID documentId;
    private String name;

}
