package com.tessi.kyc.user.handler;

import com.tessi.kyc.event.UserCreatedEvent;
import com.tessi.kyc.event.UserDeletedEvent;
import com.tessi.kyc.user.command.UserDeleteCommand;
import com.tessi.kyc.user.entity.User;
import com.tessi.kyc.user.repository.UserRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ReplayStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ProcessingGroup("user")
public class UserEventHandler {

    private final static Logger LOG = LoggerFactory.getLogger(UserEventHandler.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommandGateway commandGateway;

    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent, ReplayStatus replayStatus) {

        LOG.info("Create user view {}", userCreatedEvent.getId());

        if (userRepository.findByUsername(userCreatedEvent.getUsername()) != null) {
            LOG.warn("Duplicate user '{}' found, this one will not be registered", userCreatedEvent.getUsername());

            if(!replayStatus.isReplay()) {
                LOG.info("Not replaying, compensating action {}", userCreatedEvent.getId());
                commandGateway.sendAndWait(new UserDeleteCommand(userCreatedEvent.getId()));
            }

            return;
        }

        User user = new User();
        user.setUuid(userCreatedEvent.getId().toString());
        user.setUsername(userCreatedEvent.getUsername());
        user.setPassword(userCreatedEvent.getPassword());

        userRepository.save(user);
    }

    @EventHandler
    @Transactional
    public void on(UserDeletedEvent userDeletedEvent) {
        LOG.info("Delete user view {}", userDeletedEvent.getId());
        User userToDelete = userRepository.findByUuid(userDeletedEvent.getId().toString());
        if (userToDelete != null) userRepository.delete(userToDelete);
    }
}
