package com.tessi.kyc.user.command;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class UserCreateCommand {

    @TargetAggregateIdentifier
    private final UUID id;
    private final String username;
    private final String password;
}
