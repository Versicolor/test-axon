package com.tessi.kyc.event;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class ControlCreatedEvent {

    private final UUID controlId;
    private final UUID documentId;
    private final UUID controlTypeId;
    private final Date dateCreate;

}
