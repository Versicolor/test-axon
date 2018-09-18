package com.tessi.kyc.control.handler;

import com.tessi.kyc.event.DocumentCreatedEvent;
import com.tessi.kyc.event.FolderCreatedEvent;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Saga(sagaStore = "sagaStore", configurationBean = "controlFolderSagaConfiguration")
//@ProcessingGroup("control-folder")
public class ControlFolderSaga {

    private static final Logger LOG = LoggerFactory.getLogger(ControlFolderSaga.class);

    @StartSaga(forceNew = true)
    @SagaEventHandler(associationProperty = "folderId")
    public void on(FolderCreatedEvent event){
        LOG.info("SAGA FOLDER STARTED - Control : FolderCreatedEvent caught: {}", event);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "folderId")
    public void on(DocumentCreatedEvent event){
        LOG.info("SAGA FOLDER ENDED - Control : DocumentCreatedEvent caught {}", event);
    }
}
