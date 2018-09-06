package com.tessi.kyc.document.command;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class FolderCreateCommand {

    @TargetAggregateIdentifier
    private final UUID folderId;
    private final UUID folderTypeId;

}
