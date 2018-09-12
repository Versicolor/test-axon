package com.tessi.kyc.document.aggregate;

import com.tessi.kyc.document.command.DocumentCreateCommand;
import com.tessi.kyc.document.command.DocumentUpdateCommand;
import com.tessi.kyc.event.DocumentCreatedEvent;
import com.tessi.kyc.event.DocumentUpdatedEvent;
import lombok.Value;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.CommandHandlerInterceptor;
import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

public class DocumentAggregate {

    private final static Logger LOG = LoggerFactory.getLogger(DocumentAggregate.class);

    @EntityId
    public UUID documentId;

    public UUID folderId;

    public UUID documentTypeId;

    public Date dateCreate;

    public String name;

    public String status;

    public DocumentAggregate(UUID documentId, UUID folderId, UUID documentTypeId, Date dateCreate, String name, String status) {
        this.documentId = documentId;
        this.folderId = folderId;
        this.documentTypeId = documentTypeId;
        this.dateCreate = dateCreate;
        this.name = name;
        this.status = status;
    }

    @CommandHandler
    public void handle(DocumentUpdateCommand command) {
        LOG.info("CommandHandler {}", command);

        apply(new DocumentUpdatedEvent(command.getDocumentId(), command.getName()));
    }

    @EventSourcingHandler
    public void on(DocumentUpdatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.documentId = event.getDocumentId();
        this.name = event.getName();
    }
}