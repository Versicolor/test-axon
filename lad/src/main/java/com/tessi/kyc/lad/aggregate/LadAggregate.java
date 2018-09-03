package com.tessi.kyc.lad.aggregate;

import com.tessi.kyc.event.LadCreatedEvent;
import com.tessi.kyc.event.LadExtractedEvent;
import com.tessi.kyc.lad.command.LadCreateCommand;
import com.tessi.kyc.lad.command.LadExtractionCommand;
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
public class LadAggregate {

    private final static Logger LOG = LoggerFactory.getLogger(LadAggregate.class);

    @AggregateIdentifier
    private UUID id;

    private UUID documentId;

    private Date dateStart;

    private Date dateEnd;

    private String status;

    public LadAggregate() {
    }

    @CommandHandler
    public LadAggregate(LadCreateCommand command) {
        LOG.info("CommandHandler {}", command);

        apply(new LadCreatedEvent(command.getId(), command.getDocumentId(), new Date()));
    }

    @CommandHandler
    public void handle(LadExtractionCommand command) {
        LOG.info("CommandHandler {}", command);

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String data = "mega top lad extraction";

        apply(new LadExtractedEvent(command.getId(), command.getDocumentId(), new Date(), data));
    }

    @EventSourcingHandler
    public void on(LadCreatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.id = event.getId();
        this.documentId = event.getId();
        this.status = "CREATED";
    }

    @EventSourcingHandler
    public void on(LadExtractedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.id = event.getId();
        this.status = "EXTRACTED";
        this.dateEnd = event.getDateExtracted();
    }
}