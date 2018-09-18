package com.tessi.kyc.control.command;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class ControlExecuteCommand {

    @TargetAggregateIdentifier
    private final UUID controlId;
    private final UUID documentId;
    private final UUID controlTypeId;

}
