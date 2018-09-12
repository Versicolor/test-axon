package com.tessi.kyc.user;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.ErrorHandler;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfiguration config) {
        config.usingTrackingProcessors();
        //config.configureErrorHandler(e -> PropagatingErrorHandler.instance());
    }

    //@Bean
    //public CommandBus commandBus(TransactionManager transactionManager) {
    //    CommandBus commandBus = new AsynchronousCommandBus(Executors.newCachedThreadPool(), transactionManager, NoOpMessageMonitor.INSTANCE);
    //    return commandBus;
    //}
}
