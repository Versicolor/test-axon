package com.tessi.kyc.user.command;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class UserDeleteCommand {

    @TargetAggregateIdentifier
    private final UUID id;
}
