package com.tessi.kyc.lad.handler;

import com.tessi.kyc.event.ControlCreatedEvent;
import com.tessi.kyc.event.ControlsCreatedEvent;
import com.tessi.kyc.event.LadCreatedEvent;
import com.tessi.kyc.lad.command.LadCreateCommand;
import com.tessi.kyc.lad.command.LadExtractionCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LadHandler {

    @Autowired
    private CommandGateway commandGateway;

    @EventHandler
    public void on(ControlsCreatedEvent controlsCreatedEvent) {

        LadCreateCommand command = new LadCreateCommand(UUID.randomUUID(), controlsCreatedEvent.getDocumentId());
        commandGateway.send(command);
    }

    @EventHandler
    public void on(LadCreatedEvent ladCreatedEvent) {

        LadExtractionCommand command = new LadExtractionCommand(ladCreatedEvent.getId(), ladCreatedEvent.getDocumentId());
        commandGateway.send(command);

    }
}
