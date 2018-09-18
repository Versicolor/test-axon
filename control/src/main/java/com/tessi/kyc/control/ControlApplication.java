package com.tessi.kyc.control;

import com.tessi.kyc.control.handler.ControlDocumentSaga;
import com.tessi.kyc.control.handler.ControlFolderSaga;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.jpa.JpaSagaStore;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
public class ControlApplication {

    @Autowired
    private EurekaRegistration registration;

    @Autowired
    private EntityManagerProvider entityManagerProvider;

    public static void main(String[] args) {
        SpringApplication.run(ControlApplication.class, args);
    }


    @Autowired
    public void configure(EventProcessingConfiguration config) {
        config.registerTrackingEventProcessor("control", conf -> TrackingEventProcessorConfiguration.forSingleThreadedProcessing().andInitialSegmentsCount(1));
    }

//    @Bean
//    public TokenStore tokenStore(EntityManagerProvider entityManagerProvider) {
////        return new JpaTokenStore(entityManagerProvider, new JacksonSerializer());
//        return new JpaTokenStore(entityManagerProvider, new XStreamSerializer());
//    }
//
//    @Bean
//    public Repository<FolderAggregate> folderAggregateRepository(EventStore eventStore/*, SnapshotTriggerDefinition snapshotTriggerDefinition*/) {
//        return new EventSourcingRepository<>(FolderAggregate.class, eventStore/*, snapshotTriggerDefinition*/);
//    }

    // Example function providing a Spring Cloud Connector
    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new SpringCloudCommandRouter(discoveryClient, registration, new AnnotationRoutingStrategy());
//        return new SpringCloudCommandRouter(discoveryClient, registration, new MetaDataRoutingStrategy("documentId"));
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

    @Bean
    public SagaStore sagaStore(EntityManagerProvider emp) {
        return new JpaSagaStore(new XStreamSerializer(), emp);
    }

    @Bean
    public SagaConfiguration<ControlDocumentSaga> controlDocumentSagaConfiguration() {
//        return SagaConfiguration.trackingSagaManager(ControlDocumentSaga.class, "control-document")
        return SagaConfiguration.trackingSagaManager(ControlDocumentSaga.class)
                .configureTrackingProcessor(configuration -> TrackingEventProcessorConfiguration.forParallelProcessing(2).andInitialSegmentsCount(4));
    }

    @Bean
    public SagaConfiguration<ControlFolderSaga> controlFolderSagaConfiguration() {
//        return SagaConfiguration.trackingSagaManager(ControlFolderSaga.class, "control-folder")
        return SagaConfiguration.trackingSagaManager(ControlFolderSaga.class )
                .configureTrackingProcessor(configuration -> TrackingEventProcessorConfiguration.forParallelProcessing(1));
    }

    //@Bean
    //public CommandBus commandBus(TransactionManager transactionManager) {
    //    CommandBus commandBus = new AsynchronousCommandBus(Executors.newCachedThreadPool(), transactionManager, NoOpMessageMonitor.INSTANCE);
    //    return commandBus;
    //}
}
