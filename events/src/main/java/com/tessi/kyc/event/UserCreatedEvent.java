package com.tessi.kyc.event;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Value
public class UserCreatedEvent {

    private final UUID id;
    private final String username;
    private final String password;

}
