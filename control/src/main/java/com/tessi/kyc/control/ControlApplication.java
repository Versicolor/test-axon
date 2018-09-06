package com.tessi.kyc.control;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableSwagger2
public class ControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControlApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfiguration config) {
        config.usingTrackingProcessors();
    }

    //@Bean
    //public CommandBus commandBus(TransactionManager transactionManager) {
    //    CommandBus commandBus = new AsynchronousCommandBus(Executors.newCachedThreadPool(), transactionManager, NoOpMessageMonitor.INSTANCE);
    //    return commandBus;
    //}
}
