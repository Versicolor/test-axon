package com.tessi.kyc.control.handler;

import com.tessi.kyc.control.command.ControlCreateCommand;
import com.tessi.kyc.event.ControlsCreatedEvent;
import com.tessi.kyc.event.DocumentCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class ControlHandler {

    private final static Logger LOG = LoggerFactory.getLogger(ControlHandler.class);

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EventBus eventBus;

    private UUID controlType1 = UUID.fromString("3f696107-a820-4231-a1ca-deccb8768f7d");
    private UUID controlType2 = UUID.fromString("19af2f6a-d463-4426-b983-a1cb09a33854");

    @EventHandler
    @Transactional
    public void on(DocumentCreatedEvent documentCreatedEvent) {

       LOG.info("Received DocumentCreatedEvent {}",documentCreatedEvent);

        ControlCreateCommand command1 = new ControlCreateCommand(UUID.randomUUID(), documentCreatedEvent.getDocumentId(), controlType1);
        commandGateway.sendAndWait(command1);

        ControlCreateCommand command2 = new ControlCreateCommand(UUID.randomUUID(), documentCreatedEvent.getDocumentId(), controlType2);
        commandGateway.sendAndWait(command2);

        eventBus.publish(GenericEventMessage.asEventMessage(new ControlsCreatedEvent(documentCreatedEvent.getDocumentId())));
    }
}
