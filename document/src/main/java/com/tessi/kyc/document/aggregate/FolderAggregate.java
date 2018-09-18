package com.tessi.kyc.document.aggregate;

import com.tessi.kyc.document.command.DocumentCreateCommand;
import com.tessi.kyc.document.command.FolderCreateCommand;
import com.tessi.kyc.event.DocumentCreatedEvent;
import com.tessi.kyc.event.FolderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.commandhandling.model.CommandHandlerInterceptor;
import org.axonframework.commandhandling.model.ForwardMatchingInstances;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class FolderAggregate {

    private final static Logger LOG = LoggerFactory.getLogger(FolderAggregate.class);

    @AggregateIdentifier
    private UUID folderId;

    private UUID folderTypeId;

    private Date dateCreate;

    private String status;

    @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
    private List<DocumentAggregate> documents = new ArrayList<>();

    public FolderAggregate() {
    }

    @CommandHandler
    public FolderAggregate(FolderCreateCommand command) {
        LOG.info("CommandHandler {}", command);

        apply(new FolderCreatedEvent(command.getFolderId(), command.getFolderTypeId(), new Date()));
    }

    @CommandHandlerInterceptor
    public void intercept(DocumentCreateCommand command) {
        LOG.info("Intercept {}", command);
        if(documents.stream().anyMatch(documentAggregate -> documentAggregate.documentTypeId.equals(command.getDocumentTypeId()))) {
            throw new RuntimeException("doc type"+command.getDocumentTypeId()+" already exist in this folder");
        }
    }

    @CommandHandler
    public void on(DocumentCreateCommand command) {
        LOG.info("CommandHandler {}", command);

        apply(new DocumentCreatedEvent(command.getDocumentId(), command.getDocumentTypeId(), command.getFolderId(), new Date(), command.getName(), "IMPORTED"));
    }

    @EventSourcingHandler
    public void on(FolderCreatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.folderId = event.getFolderId();
        this.folderTypeId = event.getFolderTypeId();
        this.dateCreate = event.getDateCreated();
        this.status = "CREATED";
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);
        documents.add(new DocumentAggregate(event.getDocumentId(), folderId, event.getDocumentTypeId(), event.getDateCreated(), event.getName(), event.getStatus()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderAggregate that = (FolderAggregate) o;
        return Objects.equals(folderId, that.folderId) &&
                Objects.equals(folderTypeId, that.folderTypeId) &&
                Objects.equals(dateCreate, that.dateCreate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(documents, that.documents);
    }

    @Override
    public int hashCode() {

        return Objects.hash(folderId, folderTypeId, dateCreate, status, documents);
    }
}