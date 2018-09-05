package com.tessi.kyc.document.command;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class DocumentUpdateCommand {

    @TargetAggregateIdentifier
    private UUID id;
    private String name;
}
