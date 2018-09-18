package com.tessi.kyc.event;

import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class ExtractFieldsCommand {


    private UUID ladId;
    private UUID documentId;
    private List<UUID> fields;

}
