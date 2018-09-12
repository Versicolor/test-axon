package com.tessi.kyc.user.handler;

import com.tessi.kyc.event.UserCreatedEvent;
import com.tessi.kyc.user.entity.User;
import com.tessi.kyc.user.repository.UserRepository;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserEventHandler {

    private final static Logger LOG = LoggerFactory.getLogger(UserEventHandler.class);

    @Autowired
    private UserRepository userRepository;

    @EventHandler
    @Transactional(noRollbackFor = RuntimeException.class)
    public void on(UserCreatedEvent userCreatedEvent) {

        LOG.info("Create user view {}", userCreatedEvent.getId());

       //if(userRepository.findByUsername(userCreatedEvent.getUsername()) != null) {
       //    LOG.warn("duplicate user '{}' found, this one will not be registered", userCreatedEvent.getUsername());
       //    return;
       //}
if(true) throw new RuntimeException();
        User user = new User();
        user.setUuid(userCreatedEvent.getId().toString());
        user.setUsername(userCreatedEvent.getUsername());
        user.setPassword(userCreatedEvent.getPassword());

        userRepository.save(user);
    }
}
