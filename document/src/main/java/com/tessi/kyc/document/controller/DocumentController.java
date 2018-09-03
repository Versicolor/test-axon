package com.tessi.kyc.document.controller;

import com.tessi.kyc.document.command.DocumentCreateCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
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
    public CompletableFuture<String> createDocument(@RequestBody DocumentPost document) {
        UUID id = UUID.randomUUID();
        return commandGateway.send(new DocumentCreateCommand(id, document.getDocumentTypeId()));
    }

    /*@PutMapping("/{id}")
    public CompletableFuture<String> updateComplaintDescription(@PathVariable UUID id, CompanyUpdate companyUpdate) {
        return commandGateway.send(new ComplaintDescriptionUpdateCommand(id, companyUpdate.getDescription()));
    }*/

}