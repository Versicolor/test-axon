package com.tessi.kyc.document.controller;

import com.tessi.kyc.document.aggregate.DocumentAggregate;
import com.tessi.kyc.document.command.DocumentCreateCommand;
import com.tessi.kyc.document.command.FolderCreateCommand;
import com.tessi.kyc.document.controller.dto.FolderDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/folders")
@RestController
public class FolderController {

    private final static Logger LOG = LoggerFactory.getLogger(DocumentAggregate.class);

    private final CommandGateway commandGateway;

    public FolderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<String> createFolder(@RequestBody FolderDto folder) {
        UUID id = UUID.randomUUID();
        LOG.info("Create folder {}", id);
        return commandGateway.send(new FolderCreateCommand(id, folder.getFolderTypeId()));
    }

    @PostMapping("/doc")
    public CompletableFuture<Object> createFolderAndDocument(@RequestBody FolderDto folder) throws InterruptedException {
        UUID id = UUID.randomUUID();
        UUID folderId = commandGateway.sendAndWait(new FolderCreateCommand(id, folder.getFolderTypeId()));
        Thread.sleep(folder.getSleep());
        return commandGateway.send(new DocumentCreateCommand(UUID.randomUUID(), folderId, folder.getFolderTypeId(), "toto"))
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