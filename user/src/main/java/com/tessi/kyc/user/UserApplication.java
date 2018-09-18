package com.tessi.kyc.user;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableSwagger2
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfiguration config) {
        config.registerTrackingEventProcessor("user", conf -> TrackingEventProcessorConfiguration
                .forSingleThreadedProcessing()
                .andInitialSegmentsCount(2));
//                .configureErrorHandler(conf -> CustomEventHandler.instance());
//                .configureErrorHandler(conf -> PropagatingErrorHandler.instance());
    }

}
