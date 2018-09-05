package com.tessi.kyc.document.aggregate;

import com.tessi.kyc.document.command.DocumentCreateCommand;
import com.tessi.kyc.document.command.DocumentUpdateCommand;
import com.tessi.kyc.event.DocumentCreatedEvent;
import com.tessi.kyc.event.DocumentUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.CommandHandlerInterceptor;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class DocumentAggregate {

    private final static Logger LOG = LoggerFactory.getLogger(DocumentAggregate.class);

    @AggregateIdentifier
    private UUID documentId;

    private UUID folderId;

    private UUID documentTypeId;

    private Date dateCreate;

    private String name;

    private String status;

    public DocumentAggregate() {
    }

    @CommandHandler
    public DocumentAggregate(DocumentCreateCommand command) {
        LOG.info("CommandHandler {}", command);

        apply(new DocumentCreatedEvent(command.getId(), command.getDocumentTypeId(), new Date(), command.getName()));
    }

    @CommandHandler
    public void handle(DocumentUpdateCommand command) {
        LOG.info("CommandHandler {}", command);

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        apply(new DocumentUpdatedEvent(command.getId(), command.getName()));
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.documentId = event.getId();
        this.documentTypeId = event.getDocumentTypeId();
        this.dateCreate = event.getDateCreated();
        this.name = event.getName();
        this.status = "CREATED";
    }

    @EventSourcingHandler
    public void on(DocumentUpdatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.documentId = event.getId();
        this.name = event.getName();
    }
}