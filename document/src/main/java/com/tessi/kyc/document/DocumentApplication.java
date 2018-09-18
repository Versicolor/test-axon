package com.tessi.kyc.document;

import com.tessi.kyc.document.aggregate.FolderAggregate;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestOperations;

@EnableDiscoveryClient
@SpringBootApplication
//@EnableSwagger2
public class DocumentApplication {

    @Autowired
    private EurekaRegistration registration;

    @Autowired
    private EntityManagerProvider entityManagerProvider;

    public static void main(String[] args) {
        SpringApplication.run(DocumentApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfiguration config) {
        config.registerTrackingEventProcessor("document", conf -> TrackingEventProcessorConfiguration.forSingleThreadedProcessing().andInitialSegmentsCount(1));
    }


//    @Bean
//    public TokenStore tokenStore(EntityManagerProvider entityManagerProvider) {
//        return new JpaTokenStore(entityManagerProvider, new JacksonSerializer());
//        return new JpaTokenStore(entityManagerProvider, new XStreamSerializer());
//    }

    @Bean
    public Repository<FolderAggregate> folderAggregateRepository(EventStore eventStore/*, SnapshotTriggerDefinition snapshotTriggerDefinition*/) {
        return new EventSourcingRepository<>(FolderAggregate.class, eventStore/*, snapshotTriggerDefinition*/);
    }

    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new SpringCloudCommandRouter(discoveryClient, registration, new AnnotationRoutingStrategy());
    }

    @Bean
    public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localSegment") CommandBus commandBus,
                                                             RestOperations restOperations,
                                                             Serializer serializer) {
        return new SpringHttpCommandBusConnector(commandBus, restOperations, serializer);
    }

    @Primary // to make sure this CommandBus implementation is used for autowiring
    @Bean
    public DistributedCommandBus springCloudDistributedCommandBus(CommandRouter commandRouter,
                                                                  CommandBusConnector commandBusConnector) {
        return new DistributedCommandBus(commandRouter, commandBusConnector);
    }

    @Bean
    @Qualifier("localSegment")
    public CommandBus localSegment(TransactionManager transactionManager) {
//        SimpleCommandBus commandBus = new SimpleCommandBus();
        CommandBus commandBus = new AsynchronousCommandBus();
        commandBus.registerHandlerInterceptor(new TransactionManagingInterceptor(transactionManager));
        return commandBus;
    }
}
