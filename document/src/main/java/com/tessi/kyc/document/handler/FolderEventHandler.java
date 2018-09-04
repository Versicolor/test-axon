package com.tessi.kyc.document.handler;

import com.tessi.kyc.document.entity.Folder;
import com.tessi.kyc.document.repository.FolderRepository;
import com.tessi.kyc.event.FolderCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FolderEventHandler {

    @Autowired
    private FolderRepository folderRepository;

    @EventHandler
    public void on(FolderCreatedEvent folderCreatedEvent) {
        Folder folder = new Folder();
        folder.setUuid(folderCreatedEvent.getId().toString());
        folder.setFolderTypeId(folderCreatedEvent.getFolderTypeId().toString());
        folderRepository.save(folder);
    }

}
