package com.tessi.kyc.document;

import org.axonframework.config.EventProcessingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableSwagger2
public class DocumentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfiguration config) {
        config.usingTrackingProcessors();
    }

    //@Bean
    //public CommandBus commandBus(TransactionManager transactionManager, Repository<FolderAggregate> folderAggregateRepository) {
    //    CommandBus commandBus = new AsynchronousCommandBus(Executors.newCachedThreadPool(), transactionManager, NoOpMessageMonitor.INSTANCE);
    //    //commandBus.registerHandlerInterceptor(new DocumentCreateCommandInterceptor(folderAggregateRepository));
    //    return commandBus;
    //}
    //
    //@Bean
    //public Repository<FolderAggregate> folderAggregateRepository(EventStore eventStore/*, SnapshotTriggerDefinition snapshotTriggerDefinition*/) {
    //    return new EventSourcingRepository<FolderAggregate>(FolderAggregate.class, eventStore/*, snapshotTriggerDefinition*/);
    //}

    /*@Bean
    public SpringAggregateSnapshotterFactoryBean snapshotter() {
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, 5);
    }*/

    /*@Bean
    public SagaConfiguration sagaConfiguration() {
        return SagaConfiguration.trackingSagaManager(SagaTest.class)
                .configureTrackingProcessor(configuration -> TrackingEventProcessorConfiguration.forParallelProcessing(2));
    }*/
}
