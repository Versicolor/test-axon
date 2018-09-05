package com.tessi.kyc.event;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class LadCreatedEvent {

    private final UUID id;
    private final UUID documentId;
    private final Date dateCreate;

}
