package com.tessi.kyc.document.aggregate;

import com.tessi.kyc.document.command.DocumentCreateCommand;
import com.tessi.kyc.document.repository.FolderRepository;
import com.tessi.kyc.event.DocumentCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    private String status;

    public DocumentAggregate() {
    }

    @CommandHandler
    public DocumentAggregate(DocumentCreateCommand command, FolderRepository folderRepository) {
        LOG.info("CommandHandler {}", command);

        if(folderRepository.findByUuid(command.getFolderId().toString()) == null) {
            throw new RuntimeException("not found "+command.getFolderId().toString());
        }

        apply(new DocumentCreatedEvent(command.getId(), command.getDocumentTypeId(), new Date()));
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.documentId = event.getId();
        this.documentTypeId = event.getDocumentTypeId();
        this.dateCreate = event.getDateCreated();
        this.status = "CREATED";
    }
}