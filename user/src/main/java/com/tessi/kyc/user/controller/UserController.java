package com.tessi.kyc.user.controller;

import com.tessi.kyc.user.command.UserCreateCommand;
import com.tessi.kyc.user.dto.UserDto;
import com.tessi.kyc.user.repository.UserRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/users")
@RestController
public class UserController {

    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public UUID createUser(@RequestBody UserDto userDto) {

        if(userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new RuntimeException("User already exist");
        }

        LOG.info("Start create user");
        UUID id = UUID.randomUUID();
        commandGateway.sendAndWait(new UserCreateCommand(id, userDto.getUsername(), userDto.getPassword()));
        LOG.info("End create user");
        return id;
    }
}