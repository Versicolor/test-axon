package com.tessi.kyc.document.aggregate;

import com.tessi.kyc.document.command.DocumentCreateCommand;
import com.tessi.kyc.event.DocumentCreatedEvent;
import com.tessi.kyc.event.FolderCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.axonframework.test.matchers.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

//@RunWith(SpringRunner.class)
public class FolderAggregateTest {

    private FixtureConfiguration<FolderAggregate> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(FolderAggregate.class);
    }

    @Test
    public void createsDocument(){
        UUID documentId = UUID.randomUUID();
        UUID folderId = UUID.randomUUID();
        UUID documentTypeId = UUID.randomUUID();
        fixture.given(new FolderCreatedEvent(folderId, UUID.randomUUID(), new Date()))
                .when(new DocumentCreateCommand(documentId, folderId, documentTypeId, "doc-test"))
                .expectEventsMatching(Matchers.payloadsMatching(Matchers.exactSequenceOf(org.hamcrest.Matchers.any(DocumentCreatedEvent.class))));
    }

}