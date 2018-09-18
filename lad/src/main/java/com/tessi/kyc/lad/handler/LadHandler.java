package com.tessi.kyc.lad.handler;

import com.tessi.kyc.event.ControlsCreatedEvent;
import com.tessi.kyc.event.FieldsComputedForControls;
import com.tessi.kyc.event.FieldsExtractedEvent;
import com.tessi.kyc.event.LadCreatedEvent;
import com.tessi.kyc.lad.command.LadCreateCommand;
import com.tessi.kyc.lad.command.LadExtractionCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@ProcessingGroup("lad")
public class LadHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LadHandler.class);

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private transient EventBus eventBus;

    @EventHandler
    public void on(FieldsComputedForControls event){

        LOG.info("lad - FieldsComputedEvent caught {}", event);
        List<UUID> fieldsTypes = event.getFieldsTypes();

        //TODO documentRepository.getDocument

        Map<UUID, String> fields = new HashMap<>();

        // TODO  We call all engines needed to get all fields.
//        for (Map.Entry<UUID, List<FieldTypeEngine>> engineEntry : engines.entrySet()) {
//            Engine engine = engineRepository.findById(engineEntry.getKey());
//
//            Map<UUID, String> fieldsEngine = engineService.callEngine(engine, documentDto, engineEntry.getValue());
//
//            fields.putAll(fieldsEngine);
//
//            if (fields.keySet().containsAll(fieldTypeIds)) {
//                // we found all fields, we don't need to call other engines
//                break;
//            }
//        }

        eventBus.publish(GenericEventMessage.asEventMessage(new FieldsExtractedEvent(event.getDocumentId(), fields)));

    }

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
