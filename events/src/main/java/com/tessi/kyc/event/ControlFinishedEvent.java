package com.tessi.kyc.event;

import lombok.Value;

import java.util.UUID;

@Value
public class ControlFinishedEvent {
    private UUID documentId;
    private UUID controlId;
    private String result;
}
