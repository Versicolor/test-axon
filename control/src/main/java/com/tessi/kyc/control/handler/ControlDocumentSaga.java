package com.tessi.kyc.control.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tessi.kyc.control.command.ControlCreateCommand;
import com.tessi.kyc.control.command.ControlExecuteCommand;
import com.tessi.kyc.control.query.repository.ControlRepository;
import com.tessi.kyc.event.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Saga(sagaStore = "sagaStore", configurationBean = "controlDocumentSagaConfiguration")
//@ProcessingGroup("control-document")
public class ControlDocumentSaga {

    private final static Logger LOG = LoggerFactory.getLogger(ControlDocumentSaga.class);

    @Autowired
    private transient ControlRepository controlRepository;

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient EventBus eventBus;

    private Set<UUID> controlsToCreate = Sets.newHashSet();

    private Map<UUID, UUID> controlsToExecute = Maps.newHashMap();

    private static UUID controlType1 = UUID.fromString("3f696107-a820-4231-a1ca-deccb8768f7d");
    private static UUID controlType2 = UUID.fromString("19af2f6a-d463-4426-b983-a1cb09a33854");

    @StartSaga(forceNew = true)
    @SagaEventHandler(associationProperty = "documentId")
    public void on(DocumentCreatedEvent documentCreatedEvent) {

        LOG.info("SAGA DOCUMENT STARTED: Received DocumentCreatedEvent {}",documentCreatedEvent);

        UUID controlId1 = UUID.randomUUID();
        UUID controlId2 = UUID.randomUUID();
        controlsToExecute.put( controlId1, controlType1);
        controlsToExecute.put( controlId2, controlType2);

        controlsToCreate.add(controlId1);
        controlsToCreate.add(controlId2);

        ControlCreateCommand command1 = new ControlCreateCommand(controlId1, documentCreatedEvent.getDocumentId(), controlType1);
        commandGateway.send(command1);

        ControlCreateCommand command2 = new ControlCreateCommand(controlId2, documentCreatedEvent.getDocumentId(), controlType2);
        commandGateway.send(command2);
    }

    @SagaEventHandler(associationProperty = "documentId")
    public void on(ControlCreatedEvent event){
        LOG.info("SAGA DOCUMENT: Control - ControlCreatedEvent caught {}",event);
        boolean removed = controlsToCreate.remove(event.getControlId());

        if(!removed){
            //TODO weird should not happen
        }

        if(controlsToCreate.isEmpty()){
            LOG.info("SAGA DOCUMENT: Control - All controls have been created, computing fields to extract");
            //TODO compute fields here or at control creation time
            List<UUID> fieldsTypes = Lists.newArrayList(UUID.randomUUID(), UUID.randomUUID());

            eventBus.publish(GenericEventMessage.asEventMessage(new FieldsComputedForControls(event.getDocumentId(), fieldsTypes)));
        }
    }

    @SagaEventHandler(associationProperty = "documentId")
    public void on(FieldsExtractedEvent event) throws InterruptedException {
        LOG.info("SAGA DOCUMENT: Control - FieldsExtractedEvent caught {}", event);

        Thread.sleep(10000);/// TODO execute controlsToExecute

        //TODO find controlsToExecute to execute in view

        controlsToExecute.forEach((control, controlType) ->
        commandGateway.send(new ControlExecuteCommand(control, event.getDocumentId(), controlType1)));
    }

//    @EndSaga
    @SagaEventHandler(associationProperty = "documentId")
    public void on(ControlFinishedEvent event){
        LOG.info("SAGA DOCUMENT ENDED: Control - ControlFinishedEvent caught {}", event);
        UUID removed = controlsToExecute.remove(event.getControlId());
        if(removed != null){
            //TODO this is weird and should not happen
        }

        if(controlsToExecute.isEmpty()){

            //TODO compute status

            SagaLifecycle.end();
        }
    }
}
