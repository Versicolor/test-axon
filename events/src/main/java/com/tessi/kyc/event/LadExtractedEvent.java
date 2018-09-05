package com.tessi.kyc.event;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class LadExtractedEvent {

    private final UUID id;
    private final UUID documentId;
    private final Date dateExtracted;
    private final String data;
}
