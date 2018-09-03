package com.tessi.kyc.control.aggregate;

import com.tessi.kyc.control.command.ControlCreateCommand;
import com.tessi.kyc.event.ControlCreatedEvent;
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
public class ControlAggregate {

    private final static Logger LOG = LoggerFactory.getLogger(ControlAggregate.class);

    @AggregateIdentifier
    private UUID id;

    private UUID documentId;

    private UUID controlTypeId;

    private Date dateCreate;

    private Date dateStart;

    private Date dateEnd;

    private String status;

    public ControlAggregate() {
    }

    @CommandHandler
    public ControlAggregate(ControlCreateCommand command) {
        LOG.info("CommandHandler {}", command);

        apply(new ControlCreatedEvent(command.getId(), command.getDocumentId(), command.getControlTypeId(), new Date()));
    }

    @EventSourcingHandler
    public void on(ControlCreatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.id = event.getId();
        this.documentId = event.getDocumentId();
        this.dateCreate = event.getDateCreate();
        this.controlTypeId = event.getControlTypeId();
        this.status = "CREATED";
    }
}