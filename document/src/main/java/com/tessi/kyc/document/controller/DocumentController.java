package com.tessi.kyc.document.controller;

import com.tessi.kyc.document.command.DocumentCreateCommand;
import com.tessi.kyc.document.command.DocumentUpdateCommand;
import com.tessi.kyc.document.command.UpdateDocStatusCommand;
import com.tessi.kyc.document.controller.dto.DocumentDto;
import com.tessi.kyc.document.controller.dto.DocumentDtoName;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/documents")
@RestController
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    private final CommandGateway commandGateway;

    public DocumentController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public UUID createDocument(@RequestBody DocumentDto document) {
        LOG.info("Document - creating document");
        UUID id = UUID.randomUUID();
        commandGateway.sendAndWait(new DocumentCreateCommand(id, document.getFolderId(), document.getDocumentTypeId(), document.getName()));
        return id;
    }

    @PutMapping
    public void updateDocument(@RequestBody DocumentDtoName documentDtoName) {
        commandGateway.sendAndWait(new DocumentUpdateCommand(documentDtoName.getFolderId(), documentDtoName.getId(), documentDtoName.getName()));

    }
    @PostMapping("/status")
    public CompletableFuture<Object> updateDocumentStatus(@RequestBody UpdateStatusRequest updateStatusRequest){
        return commandGateway.send(new UpdateDocStatusCommand(updateStatusRequest.getFolderId(), updateStatusRequest.getDocumentId(), updateStatusRequest.getStatus()));
    }
}