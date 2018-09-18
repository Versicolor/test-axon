package com.tessi.kyc.document.aggregate;

import com.tessi.kyc.document.command.DocumentUpdateCommand;
import com.tessi.kyc.event.DocumentUpdatedEvent;
import com.tessi.kyc.event.FieldsExtractedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    List<FieldEntity> fields;

    public DocumentAggregate() {
    }

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

    @EventHandler
    public void on(FieldsExtractedEvent event){
        LOG.info("Document - FieldsExtractedEvent caught {}", event);
        event.getFields().forEach((k, v) -> fields.add(new FieldEntity(k, "lol", v)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentAggregate that = (DocumentAggregate) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(folderId, that.folderId) &&
                Objects.equals(documentTypeId, that.documentTypeId) &&
                Objects.equals(dateCreate, that.dateCreate) &&
                Objects.equals(name, that.name) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(documentId, folderId, documentTypeId, dateCreate, name, status);
    }
}