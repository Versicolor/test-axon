package com.tessi.kyc.control.handler;

import com.google.common.collect.Lists;
import com.tessi.kyc.control.query.entity.Document;
import com.tessi.kyc.control.query.entity.Field;
import com.tessi.kyc.control.query.repository.ControlRepository;
import com.tessi.kyc.control.query.repository.DocumentRepository;
import com.tessi.kyc.event.DocumentCreatedEvent;
import com.tessi.kyc.event.FieldsExtractedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ProcessingGroup("control")
public class ControlHandler {

    private final static Logger LOG = LoggerFactory.getLogger(ControlHandler.class);

    @Autowired
    private ControlRepository controlRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EventBus eventBus;

    @EventHandler
    public void on(FieldsExtractedEvent event){
        LOG.info("SIMPLE EVENT HANDLER: Received FieldsExtractedEvent {}", event);
        Document document = documentRepository.findByDocumentId(event.getDocumentId());

        List<Field> fields = Lists.newArrayList();
        event.getFields().forEach((k, v) -> {
            Field f = new Field();
            f.setFieldTypeId(k);
            f.setValue(v);
            f.setName("lol");
            fields.add(f);
        });
//        document.setFields(fields);

    }

    @EventHandler
    public void on(DocumentCreatedEvent documentCreatedEvent) {

        LOG.info("SIMPLE EVENT HANDLER: Received DocumentCreatedEvent {}",documentCreatedEvent);


    }
}
