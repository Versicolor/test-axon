package com.tessi.kyc.document.aggregate;

import com.tessi.kyc.document.command.FolderCreateCommand;
import com.tessi.kyc.event.FolderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class FolderAggregate {

    private final static Logger LOG = LoggerFactory.getLogger(FolderAggregate.class);

    @AggregateIdentifier
    private UUID folderId;

    private UUID folderTypeId;

    private Date dateCreate;

    private String status;

    public FolderAggregate() {
    }

    @CommandHandler
    public FolderAggregate(FolderCreateCommand command) {
        LOG.info("CommandHandler {}", command);

        apply(new FolderCreatedEvent(command.getId(), command.getFolderTypeId(), new Date()));
    }

    @EventSourcingHandler
    public void on(FolderCreatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.folderId = event.getId();
        this.folderTypeId = event.getFolderTypeId();
        this.dateCreate = event.getDateCreated();
        this.status = "CREATED";
    }
}