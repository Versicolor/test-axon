package com.tessi.kyc.document.command;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class DocumentCreateCommand {

    private final UUID documentId;

    @TargetAggregateIdentifier
    private final UUID folderId;

    private final UUID documentTypeId;

    private final String name;

}
