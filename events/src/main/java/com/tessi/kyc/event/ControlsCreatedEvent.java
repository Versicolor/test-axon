package com.tessi.kyc.event;

import lombok.Value;

import java.util.UUID;

@Value
public class ControlsCreatedEvent {

    private final UUID documentId;

}
