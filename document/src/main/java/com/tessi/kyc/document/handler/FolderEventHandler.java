package com.tessi.kyc.document.handler;

import com.tessi.kyc.document.entity.Document;
import com.tessi.kyc.document.entity.Folder;
import com.tessi.kyc.document.repository.DocumentRepository;
import com.tessi.kyc.document.repository.FolderRepository;
import com.tessi.kyc.event.DocumentCreatedEvent;
import com.tessi.kyc.event.DocumentUpdatedEvent;
import com.tessi.kyc.event.FolderCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ProcessingGroup("document")
public class FolderEventHandler {

    private final static Logger LOG = LoggerFactory.getLogger(FolderEventHandler.class);

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @EventHandler
    @Transactional
    public void on(FolderCreatedEvent folderCreatedEvent) {

        LOG.info("Create folder  view {}", folderCreatedEvent.getFolderId());

        Folder folder = new Folder();
        folder.setUuid(folderCreatedEvent.getFolderId().toString());
        folder.setFolderTypeId(folderCreatedEvent.getFolderTypeId().toString());
        folderRepository.save(folder);
    }

    @EventHandler
    @Transactional
    public void on(DocumentCreatedEvent documentCreatedEvent) {

        LOG.info("Create document view {}", documentCreatedEvent.getDocumentId());

        Document document = new Document();
        document.setDocumentId(documentCreatedEvent.getDocumentId().toString());
        document.setDocumentTypeId(documentCreatedEvent.getDocumentTypeId().toString());
        document.setDateCreate(documentCreatedEvent.getDateCreated());
        document.setFolderId(documentCreatedEvent.getFolderId().toString());
        document.setName(documentCreatedEvent.getName());
        documentRepository.save(document);
    }

    @EventHandler
    @Transactional
    public void on(DocumentUpdatedEvent documentUpdatedEvent) {

        LOG.info("Update document view {}", documentUpdatedEvent.getDocumentId());

        Document document = documentRepository.findByDocumentId(documentUpdatedEvent.getDocumentId().toString());

        document.setName(documentUpdatedEvent.getName());
    }

}
