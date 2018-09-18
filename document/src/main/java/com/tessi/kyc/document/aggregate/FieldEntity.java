package com.tessi.kyc.document.aggregate;

import lombok.Value;

import java.util.UUID;

@Value
public class FieldEntity {

    private UUID fieldTypeId;
    private String name;
    private String value;
}
