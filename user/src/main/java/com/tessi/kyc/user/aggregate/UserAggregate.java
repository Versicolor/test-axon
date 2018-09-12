package com.tessi.kyc.user.aggregate;

import com.tessi.kyc.event.UserCreatedEvent;
import com.tessi.kyc.user.command.UserCreateCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class UserAggregate {

    private final static Logger LOG = LoggerFactory.getLogger(UserAggregate.class);

    @AggregateIdentifier
    private UUID userId;

    private String username;

    private String password;

    public UserAggregate() {
    }

    @CommandHandler
    public UserAggregate(UserCreateCommand command) {
        LOG.info("CommandHandler {}", command);

        apply(new UserCreatedEvent(command.getId(), command.getUsername(), command.getPassword()));
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        LOG.info("EventSourcingHandler {}", event);

        this.userId = event.getId();
        this.username = event.getUsername();
        this.password = event.getPassword();
    }
}