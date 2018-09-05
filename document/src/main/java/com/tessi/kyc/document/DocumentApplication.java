package com.tessi.kyc.document;

import com.tessi.kyc.document.aggregate.FolderAggregate;
import com.tessi.kyc.document.handler.DocumentCreateCommandInterceptor;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableSwagger2
public class DocumentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfiguration config) {
        config.usingTrackingProcessors();
    }

    @Bean
    public CommandBus commandBus(TransactionManager transactionManager) {
        return new AsynchronousCommandBus(Executors.newCachedThreadPool(), transactionManager, NoOpMessageMonitor.INSTANCE);
    }

    @Autowired
    public void configureCommandBus(CommandBus commandBus, Repository<FolderAggregate> folderAggregateRepository) {
        commandBus.registerHandlerInterceptor(new DocumentCreateCommandInterceptor(folderAggregateRepository));
    }

    //@Bean
    //public Repository<FolderAggregate> folderAggregateRepository(EventStore eventStore/*, SnapshotTriggerDefinition snapshotTriggerDefinition*/) {
    //    return new EventSourcingRepository<>(FolderAggregate.class, eventStore/*, snapshotTriggerDefinition*/);
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
