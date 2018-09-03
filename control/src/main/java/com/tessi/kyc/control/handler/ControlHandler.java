package com.tessi.kyc.control.handler;

import com.tessi.kyc.control.command.ControlCreateCommand;
import com.tessi.kyc.event.ControlsCreatedEvent;
import com.tessi.kyc.event.DocumentCreatedEvent;
import com.tessi.kyc.event.LadExtractedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ControlHandler {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EventBus eventBus;

    private UUID controlType1 = UUID.fromString("3f696107-a820-4231-a1ca-deccb8768f7d");
    private UUID controlType2 = UUID.fromString("19af2f6a-d463-4426-b983-a1cb09a33854");

    @EventHandler
    public void on(DocumentCreatedEvent documentCreatedEvent) {

        ControlCreateCommand command1 = new ControlCreateCommand(UUID.randomUUID(), documentCreatedEvent.getId(), controlType1);
        commandGateway.send(command1);

        ControlCreateCommand command2 = new ControlCreateCommand(UUID.randomUUID(), documentCreatedEvent.getId(), controlType2);
        commandGateway.send(command2);

        eventBus.publish(GenericEventMessage.asEventMessage(new ControlsCreatedEvent(documentCreatedEvent.getId())));
    }
}
