package com.tessi.kyc.event;

import lombok.Value;

import java.util.UUID;

@Value
public class UserDeletedEvent {

    private final UUID id;

}
