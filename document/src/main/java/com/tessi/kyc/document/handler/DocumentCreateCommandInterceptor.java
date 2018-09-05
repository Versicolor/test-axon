package com.tessi.kyc.document.handler;

import com.tessi.kyc.document.aggregate.FolderAggregate;
import com.tessi.kyc.document.command.DocumentCreateCommand;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class DocumentCreateCommandInterceptor implements MessageHandlerInterceptor<CommandMessage<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentCreateCommandInterceptor.class);

    private Repository<FolderAggregate> folderAggregateRepository;

    public DocumentCreateCommandInterceptor(Repository<FolderAggregate> folderAggregateRepository) {
        this.folderAggregateRepository = folderAggregateRepository;
    }

    @Override
    public Object handle(UnitOfWork<? extends CommandMessage<?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
        CommandMessage<?> command = unitOfWork.getMessage();
        LOGGER.info("Handling a command {}.", command);
        if(command.getPayloadType().equals(DocumentCreateCommand.class)) {

            UUID folderId = ((DocumentCreateCommand) command.getPayload()).getFolderId();

            if(folderAggregateRepository.load(folderId.toString()) != null) {
                return interceptorChain.proceed();
            }
        }
        return interceptorChain.proceed();
    }
}
