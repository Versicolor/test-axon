package com.tessi.kyc.document.controller;

import com.tessi.kyc.document.aggregate.FolderAggregate;
import com.tessi.kyc.document.command.DocumentCreateCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/documents")
@RestController
public class DocumentController {

    private final CommandGateway commandGateway;

    public DocumentController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<Object> createDocument(@RequestBody DocumentPost document) {

        UUID id = UUID.randomUUID();
        return commandGateway.send(new DocumentCreateCommand(id, document.getFolderId(), document.getDocumentTypeId()))
                .handle((ok, exception) -> {

                    if(exception!=null) {
                        return exception.getMessage();
                    }

                    if(ok!=null) {
                        return ok;
                    }

                    return "should not happen";
        });
    }
}