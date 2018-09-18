package com.tessi.kyc.event;

import lombok.Value;

import java.util.Map;
import java.util.UUID;

@Value
public class FieldsExtractedEvent {

    private UUID documentId;
    private Map<UUID, String> fields;
}
